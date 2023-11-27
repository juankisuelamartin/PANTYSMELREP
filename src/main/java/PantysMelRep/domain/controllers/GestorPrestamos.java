package PantysMelRep.domain.controllers;

import PantysMelRep.domain.entities.*;
import PantysMelRep.persistencia.*;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Calendar;
import java.util.Date;

@Service
public class GestorPrestamos {

	private static final Logger logPrestamo = LoggerFactory.getLogger(PrestamoController.class);

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
	@Autowired
	private GestorPenalizaciones gestorPenalizaciones;

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

		if(gestorPenalizaciones.comprobarPenalizacion(usuario)){

			redirectAttributes.addFlashAttribute("success", "El usuario tiene una penalización activa.");
			logPrestamo.info("El usuario tiene una penalización activa.");
        }
		else{
			if (prestamoExistente != null) {
				if (prestamoExistente.isActivo()) {

					redirectAttributes.addFlashAttribute("error", "El usuario ya tiene un préstamo activo de este título.");
					logPrestamo.info("El usuario ya tiene un préstamo activo de este título.");
				} else {
					prestamoExistente.setActivo(true);
					prestamoExistente.setEjemplar(ejemplar);
					prestamoExistente.setEjemplarId(ejemplar.getId());
					prestamoExistente.setFechaInicio(fechaActual);
					prestamoExistente.setFechaFin(calendar.getTime());
					prestamoDAO.save(prestamoExistente);

					redirectAttributes.addFlashAttribute("success", "Prestamo realizado");
					logPrestamo.info("Prestamo realizado");
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

				redirectAttributes.addFlashAttribute("success", "Prestamo realizado");
				logPrestamo.info("Prestamo realizado");
			}
		}

		}

	/**
	 * @param isbn
	 * @param idUsuario
	 * @param redirectAttributes
	 */
	public void realizarDevolucion(String isbn, String idUsuario, RedirectAttributes redirectAttributes) {
		PrestamoId prestamoId = new PrestamoId(idUsuario, isbn);
		Prestamo prestamoExistente = (Prestamo) prestamoDAO.findById(prestamoId).orElse(null);
		if (prestamoExistente != null) {
			if (prestamoExistente.isActivo()) {
				if(prestamoExistente.getFechaFin().before(new Date())){

					redirectAttributes.addFlashAttribute("error", "El usuario ha devuelto el libro fuera de plazo.");
					logPrestamo.info("El usuario ha devuelto el libro fuera de plazo.");

					gestorPenalizaciones.aplicarPenalizacion(prestamoExistente.getUsuario(), prestamoExistente.getFechaFin());
				}
				else{

					redirectAttributes.addFlashAttribute("success", "El usuario ha devuelto el libro a tiempo.");
					logPrestamo.info("El usuario ha devuelto el libro a tiempo.");

				}
				prestamoExistente.setActivo(false);
				prestamoDAO.save(prestamoExistente);
			} else {

				redirectAttributes.addFlashAttribute("error", "El usuario no tiene un préstamo activo de este título.");
				logPrestamo.info("El usuario no tiene un préstamo activo de este título.");
			}
		} else {
			redirectAttributes.addFlashAttribute("error", "El usuario no tiene un préstamo activo de este título.");
			logPrestamo.info("El usuario no tiene un préstamo activo de este título.");
		}
	}

	/**
	 * @param idUsuario
	 * @param isbn
	 * @param redirectAttributes
	 */
	public void realizarReserva(String idUsuario, String isbn, RedirectAttributes redirectAttributes) {
		// Verificar si el usuario ya tiene un préstamo activo del título
		PrestamoId prestamoId = new PrestamoId(idUsuario, isbn);
		Prestamo prestamoExistente = prestamoDAO.findById(prestamoId).orElse(null);

		if (prestamoExistente != null && prestamoExistente.isActivo()) {
			redirectAttributes.addFlashAttribute("error", "El usuario ya tiene un préstamo activo de este título. No se puede realizar una reserva.");
			logPrestamo.info("El usuario ya tiene un préstamo activo de este título. No se puede realizar una reserva.");
		} else {
			// Comprobar si ya existe una reserva para este usuario y título
			ReservaId reservaId = new ReservaId(idUsuario, isbn);
			Reserva reservaExistente = (Reserva) reservaDAO.findById(reservaId).orElse(null);;

			if (reservaExistente != null) {
				redirectAttributes.addFlashAttribute("error", "El usuario ya tiene una reserva activa de este título.");
				logPrestamo.info("El usuario ya tiene una reserva activa de este título.");
			} else {
				// Crear una nueva reserva
				Usuario usuario = usuarioDAO.findById(idUsuario)
						.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
				Titulo titulo = tituloDAO.findById(isbn)
						.orElseThrow(() -> new RuntimeException("Título no encontrado"));

				Reserva reserva = new Reserva();
				reserva.setTituloId(titulo.getIsbn());
				reserva.setUsuario(usuario);
				reserva.setTitulo(titulo);
				reserva.setUsuarioId(usuario.getId());
				reserva.setFecha(new Date());

				reservaDAO.save(reserva);

				redirectAttributes.addFlashAttribute("success", "Reserva realizada con éxito.");
				logPrestamo.info("Reserva realizada con éxito.");
			}
		}
	}


}