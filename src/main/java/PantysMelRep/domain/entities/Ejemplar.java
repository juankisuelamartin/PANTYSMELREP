package PantysMelRep.domain.entities;

public class Ejemplar {

	Titulo titulo;
	private String id;

	public Ejemplar(Titulo titulo, String id) {
		this.titulo = titulo;
		this.id = id;
	}

	public Titulo getTitulo() {
		return titulo;
	}

	public void setTitulo(Titulo titulo) {
		this.titulo = titulo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
