package PantysMelRep.domain.entities;

import java.util.Collection;
import java.util.Date;

public class Usuario {

	Collection<Prestamo> prestamos;
	Collection<Reserva> reservas;
	private String id;
	private String nombre;
	private String apellidos;
	private Date fechaFinPenalizacion;
	private int attribute;

}