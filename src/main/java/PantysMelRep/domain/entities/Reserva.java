package PantysMelRep.domain.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reservas")
public class Reserva {

	@Id
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "titulo_id")
	private Titulo titulo;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	public Reserva() {
		// Constructor por defecto requerido por JPA
	}

	// Getters y setters


	public Reserva(Usuario usuario, Titulo titulo, Date fecha) {
		this.usuario = usuario;
		this.titulo = titulo;
		this.fecha = fecha;
	}



	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Titulo getTitulo() {
		return titulo;
	}

	public void setTitulo(Titulo titulo) {
		this.titulo = titulo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
