package PantysMelRep.domain.entities;

import jakarta.persistence.*;

@Entity
public class Ejemplar {

	// TODO ELIMINACION EN CASCADA DE TITULOS
	// TODO GENERATIVE COLUMN ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "titulo_id")
	private Titulo titulo;


	public Ejemplar() {
		// Constructor por defecto requerido por JPA
	}

	public Ejemplar(Titulo titulo) {
		this.titulo = titulo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
