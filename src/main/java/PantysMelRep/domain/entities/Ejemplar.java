package PantysMelRep.domain.entities;

import jakarta.persistence.*;

@Entity
public class Ejemplar {
	@Id
	@Column(name = "id")
	private String id;

	@ManyToOne
	@JoinColumn(name = "titulo_id")
	private Titulo titulo;


	public Ejemplar() {
		// Constructor por defecto requerido por JPA
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Titulo getTitulo() {
		return titulo;
	}

	public void setTitulo(Titulo titulo) {
		this.titulo = titulo;
	}

	@Override
	public String toString() {
		return String.format("Ejemplar [id=%s, titulo=%s]", id, titulo);
	}
}
