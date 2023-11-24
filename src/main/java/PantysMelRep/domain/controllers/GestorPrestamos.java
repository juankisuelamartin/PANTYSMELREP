package PantysMelRep.domain.controllers;

import PantysMelRep.domain.entities.*;
import PantysMelRep.persistencia.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

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
	@Autowired
	private EjemplarDAO ejemplarDAO;

	private static final Logger logPrestamo = LoggerFactory.getLogger(TituloController.class);

	@Transactional
	public void realizarPrestamo(String isbn, String idEjemplar, String idUsuario, RedirectAttributes redirectAttributes) {
		// Recuperar el título y el usuario a partir de los IDs
		Titulo titulo = tituloDAO.findById(isbn)
				.orElseThrow(() -> new RuntimeException("Título no encontrado"));

		Usuario usuario = usuarioDAO.findById(idUsuario)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		Ejemplar ejemplar = ejemplarDAO.findById(idEjemplar)
				.orElseThrow(() -> new RuntimeException("Ejemplar no encontrado"));
		//TODO LOG4J
		PrestamoId prestamoId = new PrestamoId(usuario.getId(), titulo.getIsbn());
		Prestamo prestamoExistente = (Prestamo) prestamoDAO.findById(prestamoId).orElse(null);

		Date fechaActual = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaActual);
		calendar.add(Calendar.DAY_OF_MONTH, 30);

		if (prestamoExistente != null) {
			if (prestamoExistente.isActivo()) {
				System.out.println("El usuario ya tiene un préstamo activo de este título.");
			} else {
				prestamoExistente.setActivo(true);
				prestamoExistente.setEjemplar(ejemplar);
				prestamoExistente.setEjemplarId(ejemplar.getId());
				prestamoExistente.setFechaInicio(fechaActual);
				prestamoExistente.setFechaFin(calendar.getTime());
				prestamoDAO.save(prestamoExistente);
				redirectAttributes.addFlashAttribute("error", "ERROR: No se ha podido realizar el prestamo");
				logPrestamo.info("ERROR: No se ha podido realizar el prestamo");
			}
		}
		else{
				Prestamo prestamo = new Prestamo();
				prestamo.setUsuario(usuario);
				prestamo.setTitulo(titulo);
				prestamo.setTituloId((titulo.getIsbn()));
				prestamo.setUsuarioId(usuario.getId());
				prestamo.setEjemplar(ejemplar);
				prestamo.setEjemplarId(ejemplar.getId());
				prestamo.setFechaInicio(fechaActual);
				prestamo.setFechaFin(calendar.getTime());
				prestamo.setActivo(true);

				prestamoDAO.save(prestamo);
			redirectAttributes.addFlashAttribute("success", "Prestamo realizado con éxito");
			logPrestamo.info("Prestamo realizado con éxito");
			}





		}

	/**
	 * 
	 * @param isbn
	 * @param idUsuario
	 */
	public void realizarDevolucion(String isbn, String idUsuario, RedirectAttributes redirectAttributes) {
		PrestamoId prestamoId = new PrestamoId(idUsuario, isbn);
		Prestamo prestamoExistente = (Prestamo) prestamoDAO.findById(prestamoId).orElse(null);
		if (prestamoExistente != null) {
			if (prestamoExistente.isActivo()) {
				if(prestamoExistente.getFechaFin().before(new Date())){
					redirectAttributes.addFlashAttribute("error", "El libro ha sido devuelto fuera de plazo");
					logPrestamo.info("El libro ha sido devuelto fuera de plazo");

					//TODO: Penalizar al usuario
				}
				else{
					redirectAttributes.addFlashAttribute("success", "El libro ha sido devuelto a tiempo");
					logPrestamo.info("El libro ha sido devuelto a tiempo");

				}
				prestamoExistente.setActivo(false);
				prestamoDAO.save(prestamoExistente);
			} else {
				redirectAttributes.addFlashAttribute("error", "El usuario no tiene un prestamo activo de este libro");
				logPrestamo.info("El usuario no tiene un prestamo activo de este libro");
			}
		} else {
			redirectAttributes.addFlashAttribute("error", "El usuario no tiene un prestamo activo de este libro");
			logPrestamo.info("El usuario no tiene un prestamo activo de este libro");
		}
	}

	/**
	 * 
	 * @param idUsuario
	 * @param isbn
	 */
	public void realizarReserva(String idUsuario, String isbn, RedirectAttributes redirectAttributes) {
		// Verificar si el usuario ya tiene un préstamo activo del título
		PrestamoId prestamoId = new PrestamoId(idUsuario, isbn);
		Prestamo prestamoExistente = prestamoDAO.findById(prestamoId).orElse(null);

		if (prestamoExistente != null && prestamoExistente.isActivo()) {
			System.out.println("El usuario ya tiene un préstamo activo de este título. No se puede realizar una reserva.");
			redirectAttributes.addFlashAttribute("error", "El usuario ya tiene un préstamo activo de este título. No se puede realizar una reserva.");
			logPrestamo.info("El usuario ya tiene un préstamo activo de este título. No se puede realizar una reserva.");
		} else {
			// Comprobar si ya existe una reserva para este usuario y título
			ReservaId reservaId = new ReservaId(idUsuario, isbn);
			Reserva reservaExistente = (Reserva) reservaDAO.findById(reservaId).orElse(null);;

			if (reservaExistente != null) {
				System.out.println("El usuario ya tiene una reserva activa de este título.");
			} else {
				// Crear una nueva reserva
				Usuario usuario = usuarioDAO.findById(idUsuario)
						.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
				Titulo titulo = tituloDAO.findById(isbn)
						.orElseThrow(() -> new RuntimeException("Título no encontrado"));

				Reserva reserva = new Reserva();
				reserva.setUsuario(usuario);
				reserva.setTitulo(titulo);
				reserva.setFecha(new Date());

				reservaDAO.save(reserva);
				System.out.println("Reserva realizada con éxito.");
			}
		}
	}


}