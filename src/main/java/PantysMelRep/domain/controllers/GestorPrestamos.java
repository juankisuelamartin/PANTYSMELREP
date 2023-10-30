package PantysMelRep.domain.controllers;

import PantysMelRep.domain.entities.Prestamo;
import PantysMelRep.domain.entities.Titulo;
import PantysMelRep.domain.entities.Usuario;
import PantysMelRep.persistencia.PrestamoDAO;
import PantysMelRep.persistencia.ReservaDAO;
import PantysMelRep.persistencia.TituloDAO;
import PantysMelRep.persistencia.UsuarioDAO;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class GestorPrestamos {

	@Autowired
	private PrestamoDAO prestamoDAO;
	@Autowired
	private ReservaDAO reservaDAO;
	@Autowired
	private TituloDAO tituloDAO;
	@Autowired
	private UsuarioDAO usuarioDAO;

	@Transactional
	public void realizarPrestamo(String isbn, String idEjemplar, String idUsuario) {
		// Recuperar el título y el usuario a partir de los IDs
		Titulo titulo = tituloDAO.findById(isbn)
				.orElseThrow(() -> new RuntimeException("Título no encontrado"));

		Usuario usuario = usuarioDAO.findById(idUsuario)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));


		// (String usuarioId, String tituloId, Usuario usuario, Titulo titulo, Date fechaInicio, Date fechaFin, Boolean activo) {
		// Crear un nuevo objeto Prestamo y establecer sus atributos
		Prestamo prestamo = new Prestamo();
		prestamo.setUsuario(usuario);
		prestamo.setTitulo(titulo);

		// Obtener la fecha actual y establecerla como la fecha de inicio
		Date fechaActual = new Date();
		prestamo.setFechaInicio(fechaActual);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaActual);
		calendar.add(Calendar.DAY_OF_MONTH, 30);
		prestamo.setFechaFin(calendar.getTime());

		prestamo.setActivo(true);

		prestamoDAO.save(prestamo);

		// Realizar otras operaciones relacionadas con el préstamo, como actualizar el ejemplar, etc.

		// Actualizar la fecha de fin en el ejemplar, si es necesario

		// Realizar otras operaciones necesarias para gestionar el préstamo

		// Notificar al usuario que el préstamo se ha realizado con éxito o manejar errores si es necesario
	}
	/**
	 * 
	 * @param isbn
	 * @param idEjemplar
	 * @param idUsuario
	 */
	public void realizarDevolucion(String isbn, String idEjemplar, String idUsuario) {
		// TODO - implement GestorPrestamos.realizarDevolucion
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idUsuario
	 * @param isbn
	 */
	public void realizarReserva(String idUsuario, String isbn) {
		// TODO - implement GestorPrestamos.realizarReserva
		throw new UnsupportedOperationException();
	}

}