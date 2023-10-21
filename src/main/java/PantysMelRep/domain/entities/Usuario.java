package PantysMelRep.domain.entities;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "usuarios")
public class Usuario {
	@Id
	private String id;

	private String nombre;
	private String apellidos;

	@Temporal(TemporalType.DATE)
	private Date fechaFinPenalizacion;

	private int attribute;

	@OneToMany(mappedBy = "usuario")
	private Collection<Prestamo> prestamos;

	@OneToMany(mappedBy = "usuario")
	private Collection<Reserva> reservas;

	public Usuario() {
	}

	public Usuario(String id, String nombre, String apellidos, Date fechaFinPenalizacion, int attribute) {
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaFinPenalizacion = fechaFinPenalizacion;
		this.attribute = attribute;
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
