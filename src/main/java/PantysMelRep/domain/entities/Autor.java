package PantysMelRep.domain.entities;

import jakarta.persistence.*;
import java.util.Collection;

@Entity
@IdClass(AutorId.class)
@Table(name = "autores")
public class Autor {
	@ManyToMany(mappedBy = "autores")
	private Collection<Titulo> titulos;

	@Id
	@Column
	private String nombre;

	@Id
	@Column
	private String apellido;

	public Autor() {
		// Constructor por defecto requerido por JPA
	}

	public Autor(String nombre, String apellido) {
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

	@Override
	public String toString() {
		return String.format("Autor [nombre=%s, apellido=%s]", nombre, apellido);
	}
}
