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

	@Column(name = "ejemplar_id")
	private Long ejemplarId;

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


	@ManyToOne
	@JoinColumn(name = "ejemplar_id", referencedColumnName = "id")
	private Ejemplar ejemplar;

	private boolean activo;

	public Prestamo() {
		// Constructor sin argumentos
	}

	public Prestamo(Usuario usuario, Titulo titulo, Date fechaInicio, Date fechaFin, Ejemplar ejemplar, boolean activo) {
		this.usuario = usuario;
		this.titulo = titulo;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.activo = activo;
		this.usuarioId = usuario.getId();  // Asignar el usuarioId aquí
		this.tituloId = titulo.getIsbn();  // Asignar el tituloId aquí
		this.ejemplarId = ejemplar.getId();
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

	public Long getEjemplarId() {
		return ejemplarId;
	}

	public void setEjemplarId(Long ejemplarId) {
		this.ejemplarId = ejemplarId;
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

	public Ejemplar getEjemplar() {
		return ejemplar;
	}

	public void setEjemplar(Ejemplar ejemplar) {
		this.ejemplar = ejemplar;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}
