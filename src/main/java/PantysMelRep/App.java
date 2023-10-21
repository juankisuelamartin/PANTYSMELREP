package PantysMelRep;
import java.sql.SQLException;

import PantysMelRep.domain.entities.Autor;
import PantysMelRep.persistencia.AgenteBBDD;
import PantysMelRep.persistencia.AutorDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Hello world!
 *
 *
 *
 */
public class App
{
    public static void main( String[] args ) throws SQLException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("db_proyecto_iso2");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        /*Autor entidad = new Autor();
        entidad.setNombre("Valor1");
        entidad.setApellido("Valor2");
        //Insertar en la base de datos
        entityManager.persist(entidad);
        //Confirmar que se ha insertado correctamente
        entityManager.getTransaction().commit();*/
        // Cerrar la conexión

        entityManager.close();

        System.out.println( "Exit." );
    }
}

