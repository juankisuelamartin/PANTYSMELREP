package PantysMelRep.domain.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@IdClass(PrestamoId.class)
@Table(name = "prestamos")
public class Prestamo {

	@EmbeddedId
	private PrestamoId id;

	@MapsId("usuarioId")
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@MapsId("tituloId")
	@ManyToOne
	@JoinColumn(name = "titulo_id")
	private Titulo titulo;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_inicio")
	private Date fechaInicio;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_fin")
	private Date fechaFin;

	private boolean activo;

	public Prestamo(PrestamoId id, Usuario usuario, Titulo titulo, Date fechaInicio, Date fechaFin, boolean activo) {
		this.id = id;
		this.usuario = usuario;
		this.titulo = titulo;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.activo = activo;
	}
	public Prestamo(){

	}

	public PrestamoId getId() {
		return id;
	}

	public void setId(PrestamoId id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
		this.id.setUsuarioId(usuario.getId());  // Set the id field here
	}

	public void setTitulo(Titulo titulo) {
		this.titulo = titulo;
		this.id.setTituloId(titulo.getIsbn());  // Set the id field here
	}

	public Titulo getTitulo() {
		return titulo;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}