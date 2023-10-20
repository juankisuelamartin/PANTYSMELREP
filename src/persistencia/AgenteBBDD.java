package persistencia;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.derby.jdbc.EmbeddedDriver;
import org.apache.log4j.Logger;


public class AgenteBBDD {

	private static Logger logJava = Logger.getLogger(AgenteBBDD.class);
	private final String logFatal = "LOG FATAL: ";

	protected Connection mBD;
	private final static String DRIVER = "jdbc:derby";
	private final static String CONNECTION_STRING = "jdbc:derby:db_proyecto_iso2;create=true";
	private final static String DBNAME = "db_proyecto_iso2";
	private final static String DBUSER = "admin";
	private final static String DBPASS = "admin";

	public AgenteBBDD() throws SQLException {
		conectarBBDD();
	}

	public void conectarBBDD() throws SQLException {
		Driver derbyEmbeddedDriver = new EmbeddedDriver();
		try {
			DriverManager.registerDriver(derbyEmbeddedDriver);
			this.mBD = DriverManager.getConnection(DRIVER + ":" + DBNAME, DBUSER, DBPASS);
		} catch (SQLException e) {
			// Si no esta creada la base de datos, la crea y se conecta
			if (((e.getErrorCode() == 40000) && ("XJ004".equals(e.getSQLState())))) {
				crearBaseDatosSinoExiste();
				conectarBBDD();
			} else {
				logJava.fatal(logFatal+e.toString());
				logJava.fatal(logFatal+e.getErrorCode());
				logJava.fatal(logFatal+e.getSQLState());
			}
		}
	}

	public void desconectarBBDD() {
		try {
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
			if (!this.mBD.isClosed())
				this.mBD.close();
		} catch (SQLException ex) {
			if (((ex.getErrorCode() == 50000) && ("XJ015".equals(ex.getSQLState())))) {
				logJava.info("LOG INFO: Derby shut down normally");
			} else {
				logJava.info("LOG INFO: Derby did not shut down normally");
				logJava.fatal(logFatal+ex.toString());
			}
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

	private void crearBaseDatosSinoExiste() throws SQLException {
		logJava.info("LOG INFO: Creando base de datos...");
		PreparedStatement pstmt = null;
		Statement stmt = null;
		String createSQL = "create table usuarios (id varchar(10) not null, "
				+ "nombre varchar(50) not null, apellidos varchar(50) not null, "
				+ "fechaFinPenalizacion date, attribute varchar(50), primary key (id))";

		try {
			// Crear la conexion y la BBDD
			Driver derbyEmbeddedDriver = new EmbeddedDriver();
			DriverManager.registerDriver(derbyEmbeddedDriver);
			this.mBD = DriverManager.getConnection(CONNECTION_STRING, DBUSER, DBPASS);
			this.mBD.setAutoCommit(false);
			stmt = this.mBD.createStatement();

			// Crear la tabla usuarios
			stmt.execute(createSQL);

			try {
				// Datos iniciales de usuarios
				pstmt = this.mBD.prepareStatement(
						"insert into USUARIOS (ID, NOMBRE, APELLIDOS, FECHAFINPENALIZACION, ATTRIBUTE) VALUES (?,?,?,?,?,?)");
				pstmt.setString(1, "00000000A");
				pstmt.setString(2, "Pepe");
				pstmt.setString(3, "Perez");
				pstmt.setString(4, "09-09-2009");
				pstmt.setString(5, "?");
				pstmt.executeUpdate();

			} catch (SQLException e) {
				logJava.fatal(logFatal+e.toString());
				logJava.fatal(logFatal+e.getErrorCode());
				logJava.fatal(logFatal+e.getSQLState());
			} finally {
				if (pstmt != null)
					pstmt.close();
			}

			// Crear la tabla ejemplares
			createSQL = "create table ejemplares (id int not null , primary key (id))";
			stmt.execute(createSQL);

			try {
				// Datos iniciales de ejemplares
				pstmt = this.mBD.prepareStatement("insert into ejemplares (id) VALUES (?)");
				pstmt.setString(1, "123445");
				pstmt.executeUpdate();

			} catch (SQLException e) {
				logJava.fatal(logFatal+e.toString());
				logJava.fatal(logFatal+e.getErrorCode());
				logJava.fatal(logFatal+e.getSQLState());
			} finally {
				if (pstmt != null)
					pstmt.close();
			}

			// Crear la tabla titulos
			createSQL = "create table titulos (titulo varchar(50) not null, isbn varchar(50) not null, numReserva varchar(50) not null, "
					+ "primary key (titulo))";
			stmt.execute(createSQL);

			try {
				pstmt = this.mBD.prepareStatement(
						"insert into titulos (titulo, isbn, numReserva) VALUES (?,?,?)");
				pstmt.setString(1, "Las flipantes aventuras de Raúl y su perra Josefina");
				pstmt.setString(2, "1231234567813");
				pstmt.setString(3, "1");
				pstmt.executeUpdate();
			} catch (SQLException e) {
				logJava.fatal(logFatal+e.toString());
				logJava.fatal(logFatal+e.getErrorCode());
				logJava.fatal(logFatal+e.getSQLState());
			} finally {
				if (pstmt != null)
					pstmt.close();
			}
			try {
				pstmt = this.mBD.prepareStatement(
						"insert into titulos (titulo, isbn, numReserva) VALUES (?,?,?)");
				pstmt.setString(1, "Biografia de Kiko Rivera");
				pstmt.setString(2, "4569876567813");
				pstmt.setString(3, "2");
				pstmt.executeUpdate();
			} catch (SQLException e) {
				logJava.fatal(logFatal+e.toString());
				logJava.fatal(logFatal+e.getErrorCode());
				logJava.fatal(logFatal+e.getSQLState());
			} finally {
				if (pstmt != null)
					pstmt.close();
			}

			// Crear la tabla Autores
			createSQL = "create table autores (nombre varchar(50) not null, apellido varchar(50) not null, "
					+ "primary key (nombre))";
			stmt.execute(createSQL);

			try {
				pstmt = this.mBD.prepareStatement(
						"insert into autores (nombre, apellido) VALUES (?,?)");
				pstmt.setString(1, "José Luis");
				pstmt.setString(2, "Bravo");
				pstmt.executeUpdate();
			} catch (SQLException e) {
				logJava.fatal(logFatal+e.toString());
				logJava.fatal(logFatal+e.getErrorCode());
				logJava.fatal(logFatal+e.getSQLState());
			} finally {
				if (pstmt != null)
					pstmt.close();
			}
			// Crear la tabla Prestamos
			createSQL = "create table prestamos (fechaInicio date, fechaFin, activo boolean, "
					+ "primary key (fechaInicio))";
			stmt.execute(createSQL);

			try {
				pstmt = this.mBD.prepareStatement(
						"insert into prestamos (fechaInicio, fechaFin, activo) VALUES (?,?,?)");
				pstmt.setString(1, "05-03-2004");
				pstmt.setString(2, "09-03-2004");
				pstmt.setString(3, String.valueOf(true));
				pstmt.executeUpdate();
			} catch (SQLException e) {
				logJava.fatal(logFatal+e.toString());
				logJava.fatal(logFatal+e.getErrorCode());
				logJava.fatal(logFatal+e.getSQLState());
			} finally {
				if (pstmt != null)
					pstmt.close();
			}
			try {
				pstmt = this.mBD.prepareStatement(
						"insert into prestamos (fechaInicio, fechaFin, activo) VALUES (?,?,?)");
				pstmt.setString(1, "07-04-2004");
				pstmt.setString(2, "02-05-2004");
				pstmt.setString(3, String.valueOf(true));
				pstmt.executeUpdate();
			} catch (SQLException e) {
				logJava.fatal(logFatal+e.toString());
				logJava.fatal(logFatal+e.getErrorCode());
				logJava.fatal(logFatal+e.getSQLState());
			} finally {
				if (pstmt != null)
					pstmt.close();
			}

			// Crear la tabla Reservas
			createSQL = "create table reservas (fecha date, "
					+ "primary key (fecha))";
			stmt.execute(createSQL);

			try {
				pstmt = this.mBD.prepareStatement(
						"insert into prestamos (fecha) VALUES (?)");
				pstmt.setString(1, "09-05-2004");
				pstmt.executeUpdate();
			} catch (SQLException e) {
				logJava.fatal(logFatal+e.toString());
				logJava.fatal(logFatal+e.getErrorCode());
				logJava.fatal(logFatal+e.getSQLState());
			} finally {
				if (pstmt != null)
					pstmt.close();
			}

			// Guardar cambios en la BD
			this.mBD.commit();

		} catch (SQLException e) {
			logJava.fatal(logFatal+e.toString());
			logJava.fatal(logFatal+e.getErrorCode());
			logJava.fatal(logFatal+e.getSQLState());
		} finally {
			if (stmt != null)
				stmt.close();
		}

		desconectarBBDD();

	}

}