package PantysMelRep.domain.entities;

import jakarta.persistence.*;

@Entity
public class Ejemplar {

	// TODO ELIMINACION EN CASCADA DE TITULOS
	// TODO GENERATIVE COLUMN ID
	//@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Id
	@Column(name = "id")
	private Long id;

	@ManyToOne //(cascade = CascadeType.ALL)
	@JoinColumn(name = "titulo_id")
	private Titulo titulo;


	public Ejemplar() {
		// Constructor por defecto requerido por JPA
	}

	public Ejemplar(Long id, Titulo titulo) {
		this.id=id;
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
