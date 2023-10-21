package PantysMelRep.domain.entities;
import java.util.Date;

public class Prestamo {

	Usuario usuario;
	Titulo titulo;
	private Date fechaInicio;
	private Date fechaFin;
	private Boolean activo;

	public Prestamo(Usuario usuario, Titulo titulo, Date fechaInicio, Date fechaFin, Boolean activo) {
		this.usuario = usuario;
		this.titulo = titulo;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.activo = activo;
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