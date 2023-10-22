package PantysMelRep.domain.entities;

import java.io.Serializable;
import java.util.Objects;

public class AutorId implements Serializable {
    private String nombre;
    private String apellido;

    public AutorId() {
    }

    public AutorId(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AutorId autorId = (AutorId) o;
        return Objects.equals(nombre, autorId.nombre) &&
                Objects.equals(apellido, autorId.apellido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, apellido);
    }
}
