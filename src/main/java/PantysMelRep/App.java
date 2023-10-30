package PantysMelRep;
import java.sql.SQLException;
import java.util.Arrays;

import PantysMelRep.domain.controllers.GestorPrestamos;
import PantysMelRep.domain.controllers.GestorTitulos;
import PantysMelRep.domain.entities.Autor;
import PantysMelRep.domain.entities.Titulo;
import PantysMelRep.persistencia.AgenteBBDD;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 *
 *
 *
 */
@SpringBootApplication
public class App {
    public static void main(String[] args) throws SQLException {
        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
        AgenteBBDD agenteBBDD = AgenteBBDD.getAgente();
        agenteBBDD.conectar();
        GestorTitulos gestorTitulos = context.getBean(GestorTitulos.class);
        gestorTitulos.setAgenteBBDD(agenteBBDD);
        GestorPrestamos gestorPrestamos = context.getBean(GestorPrestamos.class);
        gestorPrestamos.realizarPrestamo("isbn", "1", "admin");
         System.out.println("Conectado.");
    }
}

