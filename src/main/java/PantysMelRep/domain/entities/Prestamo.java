package PantysMelRep.domain.entities;
import jakarta.persistence.*;
import java.util.Date;


@Entity
@Table(name = "prestamos")
public class Prestamo {

	@Id
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
		this.usuario = usuario;
		this.titulo = titulo;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.activo = activo;
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

	@Override
	public String toString() {
		return String.format("Prestamo [usuario=%s, titulo=%s, fechaInicio=%s, fechaFin=%s, activo=%s]",
				 usuario, titulo, fechaInicio, fechaFin, activo);
	}
}
