package PantysMelRep.persistencia;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.sql.ResultSet;
import java.util.List;

public class AgenteBBDD {
    private EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    public boolean conectar() {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("dbproyectoiso2");
            entityManager = entityManagerFactory.createEntityManager();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean desconectar() {
        try {
            entityManager.close();
            entityManagerFactory.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static AgenteBBDD getAgente() {
        return new AgenteBBDD();
    }
    public static EntityManager getEntityManager() {
        return entityManager;
    }
    public int insert(String sql) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createNativeQuery(sql);
            int rowCount = ((Query) query).executeUpdate();
            entityManager.getTransaction().commit();
            return rowCount;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return -1;
        }
    }

    public ResultSet select(String sql) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createNativeQuery(sql);
            List<Object> results = query.getResultList();
            entityManager.getTransaction().commit();
            // Ahora puedes trabajar con los resultados en la lista.
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
        return null; // Debes adaptar esto según tus necesidades.
    }

    public int update(String sql) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createNativeQuery(sql);
            int rowCount = query.executeUpdate();
            entityManager.getTransaction().commit();
            return rowCount;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return -1;
        }
    }

    public int delete(String sql) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createNativeQuery(sql);
            int rowCount = query.executeUpdate();
            entityManager.getTransaction().commit();
            return rowCount;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return -1;
        }
    }
}
