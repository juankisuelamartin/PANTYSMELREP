package PantysMelRep;
import java.sql.SQLException;
import java.util.Arrays;

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

// DAR DE ALTA TITULOS
/*
        gestorTitulos.altaTitulo("titulo_23", "isbn22", Arrays.asList(new Autor("Juan Carlos", "Suela"), new Autor("Pepe", "Martin")), 2);
*/

/*
        Titulo t=new Titulo("isbn",null,Arrays.asList(new Autor("Juan Carlos", "Suela")),null, null,"jorge gei", null);
        gestorTitulos.actualizarTitulo(t, 2);
*/

      /*  gestorTitulos.borrarTitulo("isbn");*/

      /*gestorTitulos.altaEjemplar("isbn2", "Prueba");*/
        gestorTitulos.bajaEjemplar("Prueba");




 /*
 	public void borrarTitulo(String isbn) {
		Titulo titulo = tituloDAO.findById(isbn).orElseThrow(() -> new RuntimeException("Título no encontrado"));
		tituloDAO.delete(titulo);
	}
	public void altaEjemplar(String isbn, String id) {
		Titulo titulo = tituloDAO.findById(isbn).orElseThrow(() -> new RuntimeException("Título no encontrado"));
		Ejemplar ejemplar = new Ejemplar(id, titulo);
		titulo.getEjemplares().add(ejemplar);
		tituloDAO.save(titulo);
	}

	public void bajaEjemplar(String id) {
		Titulo ejemplar = ejemplarDAO.findById(id).orElseThrow(() -> new RuntimeException("Ejemplar no encontrado"));
		Titulo titulo = ejemplar;
		titulo.getEjemplares().remove(ejemplar);
		tituloDAO.save(titulo);
	}
 * */
        // public Titulo(String isbn, Collection<Ejemplar> ejemplares, Collection<Autor> autores, Collection<Prestamo> prestamos, Collection<Reserva> reservas, String titulo, String numReserva) {
        System.out.println("Exit.");
    }
}

