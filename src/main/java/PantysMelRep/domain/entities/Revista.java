/*
 * Nombre del archivo: Autor.java
 * Descripción: Clase Autor de la aplicación PantysMelRep.
 * Autor: Pan TyS Mel SA
 */
package PantysMelRep.domain.entities;

import jakarta.persistence.Entity;

@Entity
public class Revista extends Titulo {
    public Revista() {
        super();
    }

    @Override
    public String toString() {
        return "Revista{" +
                "isbn=" + getIsbn() +
                // Agregar otros campos a mostrar en el toString si es necesario
                '}';
    }
}
