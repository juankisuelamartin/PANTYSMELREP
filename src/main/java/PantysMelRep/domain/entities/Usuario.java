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

	private String contrasena;
	private String apellidos;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_fin_penalizacion")
	private Date fechaFinPenalizacion;

	@OneToMany(mappedBy = "usuario")
	private Collection<Prestamo> prestamos;

	@OneToMany(mappedBy = "usuario")
	private Collection<Reserva> reservas;

	public Usuario() {
	}

	//Objeto usuario controlladores
	public Usuario(String id, String nombre, String apellidos, Date fechaFinPenalizacion) {
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaFinPenalizacion = fechaFinPenalizacion;
	}

	//Usuario Básico para login
	public Usuario(String dni, String nombre, String apellidos, String contrasena) {
		this.id = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.contrasena=contrasena;
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

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public Date getFechaFinPenalizacion() {
		return fechaFinPenalizacion;
	}

	public void setFechaFinPenalizacion(Date fechaFinPenalizacion) {
		this.fechaFinPenalizacion = fechaFinPenalizacion;
	}

}