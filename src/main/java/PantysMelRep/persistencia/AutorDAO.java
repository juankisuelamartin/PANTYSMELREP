package PantysMelRep.persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import PantysMelRep.domain.entities.Autor;

public class AutorDAO  {

    public Autor select(String id) {
        try {
            // Crea la consulta SQL para seleccionar un Autor por su ID
            String selectSQL = "SELECT nombre, apellido FROM autores WHERE idAutor = ?";
            PreparedStatement pstmt = AgenteBBDD.getAgente().mBD.prepareStatement(selectSQL);
            pstmt.setString(1, id);

            // Ejecuta la consulta SQL y obtén el resultado
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");

                // Crea y devuelve un objeto Autor con los datos obtenidos de la base de datos
                return new Autor(null, nombre, apellido);
            } else {
                return null; // No se encontró el Autor
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Manejo de errores
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public int insert(Autor autor) {
        try {
            // Crea la consulta SQL para insertar un nuevo autor en la base de datos
            String insertSQL = "INSERT INTO autores (nombre, apellido) VALUES (?, ?)";
            PreparedStatement pstmt = AgenteBBDD.getAgente().mBD.prepareStatement(insertSQL);
            pstmt.setString(1, autor.getNombre());
            pstmt.setString(2, autor.getApellido());

            // Llama al método insert del AgenteBBDD para ejecutar la inserción
            return AgenteBBDD.getAgente().insert(pstmt);
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Manejo de errores
        }
    }

/*    public int update(Autor autor) {
        try {
            // Crea la consulta SQL para actualizar un autor en la base de datos
            String updateSQL = "UPDATE autores SET nombre = ?, apellido = ? WHERE idAutor = ?";
            PreparedStatement pstmt = AgenteBBDD.getAgente().mBD.prepareStatement(updateSQL);
            pstmt.setString(1, autor.getNombre());
            pstmt.setString(2, autor.getApellido());
            // Asegúrate de establecer el ID del autor adecuado en el PreparedStatement

            // Llama al método update del AgenteBBDD para ejecutar la actualización
            return AgenteBBDD.getAgente().update(pstmt);
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Manejo de errores
        }
    }

    public int delete(Autor autor) {
        try {
            // Crea la consulta SQL para eliminar un autor de la base de datos
            String deleteSQL = "DELETE FROM autores WHERE idAutor = ?";
            PreparedStatement pstmt = AgenteBBDD.getAgente().mBD.prepareStatement(deleteSQL);
            // Asegúrate de establecer el ID del autor adecuado en el PreparedStatement

            // Llama al método delete del AgenteBBDD para ejecutar la eliminación
            return AgenteBBDD.getAgente().delete(pstmt);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Manejo de errores
        }
    }*/
}
