/*
 * Nombre del archivo: App.java
 * Descripción: Clase principal de la aplicación PantysMelRep.
 * Autor: Pan TyS Mel SA
 */
package PantysMelRep;

import PantysMelRep.domain.controllers.GestorPrestamos;
import PantysMelRep.domain.controllers.GestorTitulos;
import PantysMelRep.domain.entities.Autor;
import PantysMelRep.domain.entities.Titulo;
import PantysMelRep.persistencia.AgenteBBDD;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
public class App {
    public static void main(String[] args){
        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
        AgenteBBDD agenteBBDD = AgenteBBDD.getAgente();
        agenteBBDD.conectar();
        GestorTitulos gestorTitulos = context.getBean(GestorTitulos.class);
        gestorTitulos.setAgenteBBDD(agenteBBDD);

         System.out.println("Conectado.");
    }
}

