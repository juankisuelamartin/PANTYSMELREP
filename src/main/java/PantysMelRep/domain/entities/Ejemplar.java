package PantysMelRep.domain.entities;

import jakarta.persistence.*;

@Entity
public class Ejemplar {
	// TODO GENERATIVE COLUMN ID
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private String id;

	@ManyToOne
	@JoinColumn(name = "titulo_id")
	private Titulo titulo;


	public Ejemplar() {
		// Constructor por defecto requerido por JPA
	}

	public Ejemplar(String id, Titulo titulo) {
		this.id = id;
		this.titulo = titulo;
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
