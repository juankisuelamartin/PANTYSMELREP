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