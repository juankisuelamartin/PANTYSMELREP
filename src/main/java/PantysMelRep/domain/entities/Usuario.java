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

	public Usuario(Collection<Prestamo> prestamos, Collection<Reserva> reservas, String id, String nombre, String apellidos, Date fechaFinPenalizacion, int attribute) {
		this.prestamos = prestamos;
		this.reservas = reservas;
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaFinPenalizacion = fechaFinPenalizacion;
		this.attribute = attribute;
	}

	public Collection<Prestamo> getPrestamos() {
		return prestamos;
	}

	public void setPrestamos(Collection<Prestamo> prestamos) {
		this.prestamos = prestamos;
	}

	public Collection<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(Collection<Reserva> reservas) {
		this.reservas = reservas;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Date getFechaFinPenalizacion() {
		return fechaFinPenalizacion;
	}

	public void setFechaFinPenalizacion(Date fechaFinPenalizacion) {
		this.fechaFinPenalizacion = fechaFinPenalizacion;
	}

	public int getAttribute() {
		return attribute;
	}

	public void setAttribute(int attribute) {
		this.attribute = attribute;
	}
}