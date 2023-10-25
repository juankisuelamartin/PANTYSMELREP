package PantysMelRep.domain.controllers;

import PantysMelRep.domain.entities.*;
import PantysMelRep.persistencia.AgenteBBDD;
import PantysMelRep.persistencia.AutorDAO;
import PantysMelRep.persistencia.EjemplarDAO;
import PantysMelRep.persistencia.TituloDAO;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;

@Service
public class GestorTitulos {

	@Autowired
	private TituloDAO tituloDAO;
	@Autowired
	EjemplarDAO ejemplarDAO;
	@Autowired
	AutorDAO autorDAO;
	private AgenteBBDD agente;

	public void setAgenteBBDD(AgenteBBDD agente) {
		this.agente = agente;
	}

	public Titulo altaTitulo(String titulo, String isbn, Collection<Autor> autores, int DType) {
		try {
			Titulo nuevoTitulo;
			if (DType == 1) {
				// Crear un nuevo libro
				Libro nuevolibro = new Libro();
				nuevolibro.setAutores(autores);
				nuevolibro.setTitulo(titulo);
				nuevolibro.setIsbn(isbn);
				nuevoTitulo = nuevolibro;
			} else {
				// Crear una nueva revista
				Revista nuevaRevista = new Revista();
				nuevaRevista.setAutores(autores);
				nuevaRevista.setTitulo(titulo);
				nuevaRevista.setIsbn(isbn);
				nuevoTitulo = nuevaRevista;
			}

			// Guardar el nuevo título en la base de datos
			tituloDAO.save(nuevoTitulo);
			System.out.println("Título guardado en la base de datos");

			return nuevoTitulo;
		} catch (DataIntegrityViolationException e) {
			// Manejar la excepción aquí...
			e.printStackTrace();
			return null;
		}
	}



	/*        Libro entidad2 = new Libro();
        entidad2.setIsbn("1232");
        entidad2.setTitulo("PRUEBA2");
        entidad2.setAutores(Arrays.asList(new Autor("JOSELUIS2", "fg"), new Autor("Pepe2", "S")));
*/
	public void actualizarTitulo(Titulo t, int DType) {
		// Buscar el título en la base de datos
		Titulo titulo = tituloDAO.findById(t.getIsbn()).orElseThrow(() -> new RuntimeException("Título no encontrado"));

		Titulo nuevoTitulo;

		if (DType == 1) {
			// Crear un nuevo libro
			nuevoTitulo = new Libro();
		} else {
			// Crear una nueva revista
			nuevoTitulo = new Revista();
		}

		copiarDatos(t, nuevoTitulo);

		// Eliminar el título antiguo y guardar el nuevo
		tituloDAO.delete(titulo);
		tituloDAO.save(nuevoTitulo);
	}

	static void copiarDatos(Titulo origen, Titulo destino) {
		destino.setAutores(origen.getAutores());
		destino.setTitulo(origen.getTitulo());
		destino.setIsbn(origen.getIsbn());
		destino.setNumReserva(origen.getNumReserva());
		destino.setEjemplares(origen.getEjemplares());
		destino.setPrestamos(origen.getPrestamos());
	}



	public void borrarTitulo(String isbn) {
		Titulo titulo = tituloDAO.findById(isbn).orElseThrow(() -> new RuntimeException("Título no encontrado"));
		tituloDAO.delete(titulo);
	}
	@Transactional
	public void altaEjemplar(String isbn) {
		// Iniciar una transacción
		agente.getEntityManager().getTransaction().begin();

		// Buscar el título en la base de datos
		Titulo titulo = tituloDAO.findById(isbn).orElseThrow(() -> new RuntimeException("Título no encontrado"));

		// Crear un nuevo ejemplar
		Ejemplar ejemplar = new Ejemplar(titulo);

		// Añadir el nuevo ejemplar a la lista de ejemplares del título
		titulo.getEjemplares().add(ejemplar);

		// Persistir el nuevo ejemplar en la base de datos
		agente.getEntityManager().persist(ejemplar);

		// Confirmar la transacción
		agente.getEntityManager().getTransaction().commit();
	}




	public void bajaEjemplar(String id) {
		// Iniciar una transacción
		agente.getEntityManager().getTransaction().begin();

		// Buscar el ejemplar en la base de datos
		Ejemplar ejemplar = agente.getEntityManager().find(Ejemplar.class, id);

		if (ejemplar != null) {
			// Eliminar el ejemplar de la base de datos
			agente.getEntityManager().remove(ejemplar);
		}

		// Confirmar la transacción
		agente.getEntityManager().getTransaction().commit();
	}







}
