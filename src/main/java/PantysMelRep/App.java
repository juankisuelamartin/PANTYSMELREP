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

/***************************************************************
 * Encabezado de Propiedad y Licencia para la Aplicación Pantysmel
 ***************************************************************/

/**
 * @file PantysmelApp.java
 * @brief Implementación principal de la aplicación Pantysmel para la biblioteca.
 *
 * @author [Pan TyS Mel SA]
 * @author [Juan Carlos Suela]
 * @author [Sergio del Pino]
 * @author [Marcos Illan]
 * @author [Victor Perez]
 * @version 1.0
 * @date [07/12/2023]
 *
 * @copyright (c) [2023]
 * Todos los derechos reservados.
 *
 * @license
 * Esta aplicación Pantysmel está bajo la Licencia [Nombre de la Licencia].
 * Puede obtener una copia de la licencia en [URL de la Licencia].
 * A menos que sea requerido por la ley aplicable o acordado por escrito,
 * el software se distribuye "TAL CUAL", SIN GARANTÍAS O CONDICIONES DE
 * NINGÚN TIPO, ya sea expresa o implícita.
 *
 * @see [https://github.com/juankisuelamartin/PANTYSMELREP]
 */
@SpringBootApplication
public class App {
    public static void main(String[] args){
        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
        AgenteBBDD agenteBBDD = AgenteBBDD.getAgente();
        agenteBBDD.conectar();

         System.out.println("Conectado.");
    }
}

