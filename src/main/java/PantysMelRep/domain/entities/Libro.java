package PantysMelRep.domain.entities;

import jakarta.persistence.*;
import java.util.Collection;

@Entity
public class Libro extends Titulo {
    public Libro() {
        super();
    }

    @Override
    public String toString() {
        return "Libro{" +
                "isbn=" + getIsbn() +
                // Agregar otros campos a mostrar en el toString si es necesario
                '}';
    }
}
