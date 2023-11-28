/*
 * Nombre del archivo: Autor.java
 * Descripción: Clase Autor de la aplicación PantysMelRep.
 * Autor: Pan TyS Mel SA
 */
package PantysMelRep.domain.entities;

import jakarta.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "autores")
public class Autor {
	@ManyToMany(mappedBy = "autores")
	private Collection<Titulo> titulos;

	@EmbeddedId
	private AutorId id;

	public Autor() {
		// Constructor por defecto requerido por JPA
	}

	public Autor(String nombre, String apellido) {
		this.id = new AutorId(nombre, apellido);
	}

	public Collection<Titulo> getTitulos() {
		return titulos;
	}

	public void setTitulos(Collection<Titulo> titulos) {
		this.titulos = titulos;
	}

	public AutorId getId() {
		return id;
	}

	public void setId(AutorId id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return String.format("Autor [nombre=%s, apellido=%s]", id.getNombre(), id.getApellido());
	}
}
