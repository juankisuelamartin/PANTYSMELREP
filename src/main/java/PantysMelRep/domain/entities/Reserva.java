package PantysMelRep.domain.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@IdClass(ReservaId.class)
@Table(name = "reservas")
public class Reserva {

	@Id
	@Column(name = "usuario_id")
	private String usuarioId;

	@Id
	@Column(name = "titulo_id")
	private String tituloId;

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

		this.titulo = titulo;
		this.fecha = fecha;
		this.usuario=usuario;
		this.usuarioId = usuario.getId();
		this.tituloId = titulo.getIsbn();
	}
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
		this.usuarioId = usuarioId;
	}

	public Titulo getTitulo() {
		return titulo;
	}

	public void setTitulo(Titulo titulo) {

		this.tituloId = tituloId;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
