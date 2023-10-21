package PantysMelRep.domain.entities;

import jakarta.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "autores")
public class Autor {
	@ManyToMany(mappedBy = "autores")
	private Collection<Titulo> titulos;

	@Id
	@Column
	private String nombre;

	@Column
	private String apellido;

	public Autor() {
		// Constructor por defecto requerido por JPA
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
