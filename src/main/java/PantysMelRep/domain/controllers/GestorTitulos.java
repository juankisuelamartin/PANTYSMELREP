/*
 * Nombre del archivo: GestorTitulos.java
 * Descripción: Clase GestorTitulos de la aplicación PantysMelRep.
 * Autor: Pan TyS Mel SA
 */
package PantysMelRep.domain.controllers;

import PantysMelRep.domain.entities.Titulo;
import PantysMelRep.domain.entities.Revista;
import PantysMelRep.domain.entities.Libro;
import PantysMelRep.domain.entities.Autor;
import PantysMelRep.domain.entities.Prestamo;
import PantysMelRep.domain.entities.Ejemplar;
import PantysMelRep.persistencia.*;


import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.Collection;


@Service
public class GestorTitulos {

	@Autowired
	private TituloDAO tituloDAO;
	@Autowired
	EjemplarDAO ejemplarDAO;
	@Autowired
	AutorDAO autorDAO;
	@Autowired
	PrestamoDAO prestamoDAO;


	private static final Logger logTitulo = LoggerFactory.getLogger(TituloController.class);



	// Gestor
	public Titulo altaTitulo(String titulo, String isbn, Collection<Autor> autores, int DType, byte[] fotoBytes, RedirectAttributes redirectAttributes) {
		try {
			// Comprobar si el ISBN ya existe en la base de datos
			if (tituloDAO.findById(isbn).isPresent()) {
				// El ISBN ya existe, mostrar un mensaje de error
				logTitulo.info("El ISBN ya existe en la Base de Datos.");
				redirectAttributes.addFlashAttribute("error", "Error: El ISBN ya existe en la Base de Datos.");
			}

			// Crear un nuevo libro o revista según DType
			Titulo nuevoTitulo;
			if (DType == 1) {
				Libro nuevolibro = new Libro();
				nuevolibro.setAutores(autores);
				nuevolibro.setTitulo(titulo);
				nuevolibro.setIsbn(isbn);
				nuevolibro.setFoto(fotoBytes);
				nuevoTitulo = nuevolibro;
			} else {
				Revista nuevaRevista = new Revista();
				nuevaRevista.setAutores(autores);
				nuevaRevista.setTitulo(titulo);
				nuevaRevista.setIsbn(isbn);
				nuevaRevista.setFoto(fotoBytes);
				nuevoTitulo = nuevaRevista;
			}

			// Guardar el nuevo título en la base de datos
			tituloDAO.save(nuevoTitulo);
			logTitulo.info("El título ha sido dado de alta con éxito.");
			redirectAttributes.addFlashAttribute("success", "El título ha sido dado de alta con éxito");
			return nuevoTitulo;
		} catch (DataIntegrityViolationException e) {
			// Manejar la excepción aquí...
			logTitulo.info("ERROR: No se ha podido dar de alta el título.");
			redirectAttributes.addFlashAttribute("error", "ERROR: No se ha podido dar de alta el título.");
			return null;
		}

	}







	public void borrarTitulo(String isbn, RedirectAttributes redirectAttributes) {
		Titulo titulo = tituloDAO.findById(isbn).orElseThrow(() -> new RuntimeException("Título no encontrado"));
		tituloDAO.delete(titulo);
	}
	@Transactional
	public void altaEjemplar(String isbn, RedirectAttributes redirectAttributes) {
		try {
			// Buscar el título en la base de datos
			Titulo titulo = tituloDAO.findById(isbn)
					.orElseThrow(() -> new RuntimeException("Título no encontrado"));

			// Generar un nuevo ID automáticamente para el nuevo ejemplar
			Long nuevoId = generarNuevoIdParaEjemplar();
			// Crear un nuevo ejemplar
			Ejemplar ejemplar = new Ejemplar();
			ejemplar.setId(nuevoId);
			ejemplar.setTitulo(titulo);

			// Añadir el nuevo ejemplar a la lista de ejemplares del título
			ejemplarDAO.save(ejemplar);
			logTitulo.info("Ejemplar dado de alta con éxito.");
			redirectAttributes.addFlashAttribute("success", "Ejemplar dado de alta con éxito");

		} catch (RuntimeException e) {
			logTitulo.error("ERROR: Título del ejemplar no encontrado", e);
			redirectAttributes.addFlashAttribute("error", "ERROR: Título del ejemplar no encontrado");
			throw e; // Lanza la excepción para que pueda ser manejada o registrada por el código que llama a este método
		}
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
	public void bajaEjemplar(String id, RedirectAttributes redirectAttributes) {

		// Buscar el ejemplar en la base de datos
		Ejemplar ejemplar = ejemplarDAO.findById(id).orElseThrow(() -> new RuntimeException("Ejemplar no encontrado"));

			if (ejemplar != null) {
				if (prestamoDAO.findByejemplarId(Long.parseLong(id)).isPresent()) {
					Prestamo prestamo = prestamoDAO.findByejemplarId(Long.parseLong(id)).orElseThrow(() -> new RuntimeException("Prestamo no encontrado"));
					if (prestamo != null) {
						if (prestamo.isActivo()) {
							logTitulo.error("El ejemplar no se puede borrar porque está prestado");
							redirectAttributes.addFlashAttribute("error", "El ejemplar no se puede borrar porque está prestado");
						} else {
							prestamoDAO.delete(prestamo);
							ejemplarDAO.delete(ejemplar);
							logTitulo.info("Ejemplar borrado. y prestamo inactivo borrado.");
							redirectAttributes.addFlashAttribute("success", "Ejemplar borrado. y prestamo inactivo borrado.");
						}
					}
				} else {
					ejemplarDAO.delete(ejemplar);
					logTitulo.info("Ejemplar borrado.");
					redirectAttributes.addFlashAttribute("success", "Ejemplar borrado.");


				}
			}

	}





}
