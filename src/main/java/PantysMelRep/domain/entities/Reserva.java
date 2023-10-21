package PantysMelRep.domain.entities;
import java.util.Date;

public class Reserva {

	Usuario usuario;
	Titulo titulo;
	private Date fecha;

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