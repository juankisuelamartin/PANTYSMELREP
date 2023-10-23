package PantysMelRep.domain.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@IdClass(PrestamoId.class)
@Table(name = "prestamos")
public class Prestamo {

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
	private Date fechaInicio;

	@Temporal(TemporalType.DATE)
	private Date fechaFin;

	private Boolean activo;

	public Prestamo() {
	}

	public Prestamo(Usuario usuario, Titulo titulo, Date fechaInicio, Date fechaFin, Boolean activo) {
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.activo = activo;
		this.usuario = usuario;
		this.titulo = titulo;
		this.usuarioId = usuario.getId();
		this.tituloId = titulo.getIsbn();
	}

	public String getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getTituloId() {
		return tituloId;
	}

	public void setTituloId(String tituloId) {
		this.tituloId = tituloId;
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

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
}
