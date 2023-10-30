package PantysMelRep.domain.controllers;

import PantysMelRep.domain.entities.*;
import PantysMelRep.persistencia.AgenteBBDD;
import PantysMelRep.persistencia.AutorDAO;
import PantysMelRep.persistencia.EjemplarDAO;
import PantysMelRep.persistencia.TituloDAO;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


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









	public void borrarTitulo(String isbn) {
		Titulo titulo = tituloDAO.findById(isbn).orElseThrow(() -> new RuntimeException("Título no encontrado"));
		tituloDAO.delete(titulo);
		System.out.println("Titulo y ejemplares borrados");
	}
	@Transactional
	public void altaEjemplar(String isbn) {

		// Buscar el título en la base de datos
		Titulo titulo = tituloDAO.findById(isbn).orElseThrow(() -> new RuntimeException("Título no encontrado"));

		// Generar un nuevo ID automáticamente para el nuevo ejemplar
		Long nuevoId = generarNuevoIdParaEjemplar();
		System.out.println(nuevoId);
		// Crear un nuevo ejemplar
		Ejemplar ejemplar = new Ejemplar();
		ejemplar.setId(nuevoId);
		ejemplar.setTitulo(titulo);

		// Añadir el nuevo ejemplar a la lista de ejemplares del título

		System.out.println("Ejemplar añadido");
		System.out.println(ejemplar.toString());
		ejemplarDAO.save(ejemplar);
	}
	private Long generarNuevoIdParaEjemplar() {
		// Buscar el ID máximo actual en los ejemplares existentes
		Long maxId = ejemplarDAO.findMaxId();

		// Generar el nuevo ID sumando 1 al ID máximo encontrado
		if (maxId != null) {
			return maxId + 1;
		} else {
			// Si no hay ejemplares existentes, asignar el ID inicial (por ejemplo, 1)
			return 1L;
		}
	}


	@Transactional
	public void bajaEjemplar(String id) {
		// Buscar el ejemplar en la base de datos
		Ejemplar ejemplar = ejemplarDAO.findById(id).orElseThrow(() -> new RuntimeException("Ejemplar no encontrado"));
		if (ejemplar != null) {
			ejemplarDAO.delete(ejemplar);
			System.out.println("Ejemplar borrado");
		}
	}






}
