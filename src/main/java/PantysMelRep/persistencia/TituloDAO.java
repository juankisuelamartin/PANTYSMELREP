package PantysMelRep.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import PantysMelRep.domain.entities.Titulo;

public class TituloDAO/* extends EntityDAO*/ {
  /*  private Connection connection;
    public TituloDAO(Connection connection) {
        this.connection = connection;
    }
    @Override
    public Titulo select(String isbn) {
        String selectQuery = "SELECT * FROM titulos WHERE isbn = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, isbn);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Map the database results to a Titulo object
                Titulo titulo = new Titulo(
                        null, // You can set autores, ejemplares, prestamos, and reservas as needed
                        resultSet.getString("titulo"),
                        resultSet.getString("isbn"),
                        resultSet.getString("numReserva")
                );
                return titulo;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection);
        }

        return null;
    }

    @Override
    public int insert(Object entity) {
        if (entity instanceof Titulo) {
            Titulo titulo = (Titulo) entity;
            Connection connection = ConnectionFactory.getConnection();
            String query = "INSERT INTO Titulo (titulo, isbn, numReserva) VALUES (?, ?, ?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, titulo.getTitulo());
                preparedStatement.setString(2, titulo.getIsbn());
                preparedStatement.setString(3, titulo.getNumReserva());

                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionFactory.closeConnection(connection);
            }
        }
        return 0;
    }

    @Override
    public int update(Object entity) {
        if (entity instanceof Titulo) {
            Titulo titulo = (Titulo) entity;
            Connection connection = ConnectionFactory.getConnection();
            String query = "UPDATE Titulo SET titulo = ?, numReserva = ? WHERE isbn = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, titulo.getTitulo());
                preparedStatement.setString(2, titulo.getNumReserva());
                preparedStatement.setString(3, titulo.getIsbn());

                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionFactory.closeConnection(connection);
            }
        }
        return 0;
    }

    @Override
    public int delete(Object entity) {
        if (entity instanceof Titulo) {
            Titulo titulo = (Titulo) entity;
            Connection connection = ConnectionFactory.getConnection();
            String query = "DELETE FROM Titulo WHERE isbn = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, titulo.getIsbn());

                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionFactory.closeConnection(connection);
            }
        }
        return 0;
    }*/
}
