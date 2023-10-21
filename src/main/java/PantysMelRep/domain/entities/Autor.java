package PantysMelRep.domain.entities;

import java.util.Collection;

public class Autor {

	Collection<Titulo> titulos;
	private String nombre;
	private String apellido;

	public Autor(Collection<Titulo> titulos, String nombre, String apellido) {
		this.titulos = titulos;
		this.nombre = nombre;
		this.apellido = apellido;
	}

	public Collection<Titulo> getTitulos() {
		return titulos;
	}

	public void setTitulos(Collection<Titulo> titulos) {
		this.titulos = titulos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
}