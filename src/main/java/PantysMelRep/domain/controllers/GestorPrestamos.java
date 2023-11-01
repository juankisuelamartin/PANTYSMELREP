package PantysMelRep.domain.controllers;

import PantysMelRep.domain.entities.*;
import PantysMelRep.persistencia.*;

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
	@Autowired
	private EjemplarDAO ejemplarDAO;

	@Transactional
	public void realizarPrestamo(String isbn, String idEjemplar, String idUsuario) {
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
				System.out.println("El usuario ya tiene un préstamo activo de este título.");
			}





		}

	/**
	 * 
	 * @param isbn
	 * @param idUsuario
	 */
	public void realizarDevolucion(String isbn, String idUsuario) {
		PrestamoId prestamoId = new PrestamoId(idUsuario, isbn);
		Prestamo prestamoExistente = (Prestamo) prestamoDAO.findById(prestamoId).orElse(null);
		if (prestamoExistente != null) {
			if (prestamoExistente.isActivo()) {
				if(prestamoExistente.getFechaFin().before(new Date())){
					System.out.println("El usuario ha devuelto el libro fuera de plazo.");

					//TODO: Penalizar al usuario
				}
				else{
					System.out.println("El usuario ha devuelto el libro a tiempo.");

				}
				prestamoExistente.setActivo(false);
				prestamoDAO.save(prestamoExistente);
			} else {
				System.out.println("El usuario no tiene un préstamo activo de este título.");
			}
		} else {
			System.out.println("El usuario no tiene un préstamo activo de este título.");
		}
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