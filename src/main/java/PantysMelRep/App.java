/*
 * Nombre del archivo: App.java
 * Descripción: Clase principal de la aplicación PantysMelRep.
 * Autor: Pan TyS Mel SA
 */
package PantysMelRep;
import PantysMelRep.persistencia.AgenteBBDD;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class App {
    public static void main(String[] args){
        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
        AgenteBBDD agenteBBDD = AgenteBBDD.getAgente();
        agenteBBDD.conectar();

         System.out.println("Conectado.");
    }
}

