package PantysMelRep.persistencia;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgenteBBDD {

	private static Logger logJava = Logger.getLogger(AgenteBBDD.class);
	private final String logFatal = "LOG FATAL: ";
	protected static AgenteBBDD mInstancia = null;
	protected static Connection mBD;
	private final static String CONNECTION_STRING = "jdbc:mysql://localhost:3306/db_proyecto_iso2";
	private final static String DBUSER = "root";
	private final static String DBPASS = "root";

	public AgenteBBDD() throws SQLException {
		conectarBBDD();
	}
	public void conectarBBDD() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el controlador MySQL
			this.mBD = DriverManager.getConnection(CONNECTION_STRING, DBUSER, DBPASS);
			System.out.println("Conexión establecida con la base de datos.");
			// La conexión se estableció correctamente, no es necesario crear la base de datos aquí
		} catch (ClassNotFoundException e) {
			logJava.fatal(logFatal + "No se pudo cargar el controlador JDBC de MySQL.");
			e.printStackTrace();
		} catch (SQLException e) {
			if (e.getErrorCode() == 1049) {
				// La base de datos no existe o la conexión falló
				System.out.println("La base de datos no existe o la conexión falló. Creando la base de datos...");
				logJava.info("La base de datos no existe o la conexión falló. Creando la base de datos...");
				crearBaseDatosSinoExiste();
				// Intentar conectar nuevamente después de crear la base de datos
				conectarBBDD();
			} else {
				// Otro error
				logJava.fatal(logFatal + e.toString());
				logJava.fatal(logFatal + e.getErrorCode());
				logJava.fatal(logFatal + e.getSQLState());
			}
		}
	}

	public void desconectarBBDD() {
		try {
			if (this.mBD != null && !this.mBD.isClosed()) {
				this.mBD.close();
			}
		} catch (SQLException ex) {
			logJava.info("LOG INFO: Error al desconectar la base de datos");
			logJava.fatal(logFatal + ex.toString());
		}
	}

	/**
	 *
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public List<Object> select(String sql) throws SQLException {
		List<Object> resultado = new ArrayList<Object>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = this.mBD.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				List<Object> v = new ArrayList<Object>();
				int i = 1;
				boolean j = true;
				while (j) {
					try {
						v.add(rs.getObject(i));
						i++;
					} catch (SQLException e) {
						j = false;
					}
				}
				resultado.add(v);
			}

			stmt.close();
		} catch (SQLException e) {
			logJava.fatal(logFatal+e.toString());
		} finally {
			if (stmt != null)
				stmt.close();
		}

		desconectarBBDD();
		return resultado;
	}

	/**
	 * @param pstmt
	 * @return res * 0 si se ha insertado correctamente * -1 si se produce un error
	 */
	public int insert(PreparedStatement pstmt) {
		int res = -1;
		try {
			res = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			logJava.fatal("LOG FATAL Insert: "+e.toString());
		}

		if (res == 0) // Si devuelve 0, es que no se ha insertado ninguna fila --> Incorrecto
			res = -1;

		if (res == 1) // Se ha insertado 1 fila --> Correcto
			res = 0;

		desconectarBBDD();
		return res;
	}

	/**
	 *
	 * @param sql
	 * @return res * 0 si se ha actualizado correctamente * -1 si se produce un
	 *         error
	 * @throws SQLException
	 */
	public int update(String sql) throws SQLException {
		conectarBBDD();
		PreparedStatement stmt = null;
		int res = -1;
		try {
			stmt = this.mBD.prepareStatement(sql);
			res = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			logJava.fatal(logFatal+e.toString());
		} finally {
			if (stmt != null)
				stmt.close();
		}

		if (res == 0) // Si devuelve 0, es que no se ha actualizado ninguna fila --> Incorrecto
			res = -1;

		if (res == 1) // Se ha actualizado 1 fila --> Correcto
			res = 0;

		desconectarBBDD();
		return res;
	}

	/**
	 *
	 * @param sql
	 * @return res * * 0 si se ha eliminado correctamente * -1 si se produce un
	 *         error
	 * @throws SQLException
	 */
	public int delete(String sql) throws SQLException {
		conectarBBDD();
		PreparedStatement stmt = null;
		int res = -1;
		try {
			stmt = this.mBD.prepareStatement(sql);
			res = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			logJava.fatal(logFatal+e.toString());
		} finally {
			if (stmt != null)
				stmt.close();
		}

		if (res == 0) // Si devuelve 0, es que no se ha eliminado ninguna fila --> Incorrecto
			res = -1;

		if (res == 1) // Se ha eliminado 1 fila --> Correcto
			res = 0;

		desconectarBBDD();
		return res;
	}
	public static AgenteBBDD getAgente() throws Exception {
		if (mInstancia == null) {
			mInstancia = new AgenteBBDD();
		}
		return mInstancia;
	}

	private void crearBaseDatosSinoExiste() throws SQLException {
		logJava.info("LOG INFO: Creando base de datos...");

		// Crear la tabla usuarios si no existe
		String createUsuariosSQL = "CREATE TABLE IF NOT EXISTS usuarios (id varchar(10) not null, " +
				"nombre varchar(50) not null, apellidos varchar(50) not null, " +
				"fechaFinPenalizacion date, attribute varchar(50), primary key (id))";

		// Crear la tabla ejemplares si no existe
		String createEjemplaresSQL = "CREATE TABLE IF NOT EXISTS ejemplares (id INT not null, primary key (id), titulo_id varchar(50),"+
				" FOREIGN KEY (titulo_id) REFERENCES titulos(isbn))";

		// Crear la tabla titulos si no existe
		String createTitulosSQL = "CREATE TABLE IF NOT EXISTS titulos (isbn varchar(50) not null,titulo varchar(50) not null, " +
				" numReserva varchar(50) not null, primary key (isbn))";

		// Crear la tabla Autores si no existe
		String createAutoresSQL = "CREATE TABLE IF NOT EXISTS autores (nombre VARCHAR(50) NOT NULL, " +
				"apellido VARCHAR(50) NOT NULL, titulo_id varchar(50), PRIMARY KEY (nombre), FOREIGN KEY (titulo_id) REFERENCES titulos(isbn))";


		// Crear la tabla Prestamos si no existe
		String createPrestamosSQL = "CREATE TABLE IF NOT EXISTS prestamos (fechaInicio date, fechaFin date, activo boolean, " +
				"primary key (fechaInicio))";

		// Crear la tabla Reservas si no existe
		String createReservasSQL = "CREATE TABLE IF NOT EXISTS reservas (fecha date, primary key (fecha))";

		try {
			// Create a connection without specifying a database
			this.mBD = DriverManager.getConnection("jdbc:mysql://localhost:3306/", DBUSER, DBPASS);

			// Create the database if it doesn't exist
			Statement createDatabaseStatement = this.mBD.createStatement();
			createDatabaseStatement.executeUpdate("CREATE DATABASE IF NOT EXISTS db_proyecto_iso2");
			createDatabaseStatement.close();

			// Now that the database exists, switch to it
			this.mBD = DriverManager.getConnection(CONNECTION_STRING, DBUSER, DBPASS);
			this.mBD.setAutoCommit(false);

			// Create the tables (as before)
			Statement stmt = this.mBD.createStatement();
			System.out.println("Creando tablas...");
			stmt.execute(createTitulosSQL);
			stmt.execute(createEjemplaresSQL);
			stmt.execute(createUsuariosSQL);
			stmt.execute(createAutoresSQL);
			stmt.execute(createPrestamosSQL);
			stmt.execute(createReservasSQL);
			stmt.close();

			// Commit changes in the database
			this.mBD.commit();
		} catch (SQLException e) {
			logJava.fatal(logFatal + e.toString());
			logJava.fatal(logFatal + e.getErrorCode());
			logJava.fatal(logFatal + e.getSQLState());
		} finally {
			desconectarBBDD();
		}
	}

}