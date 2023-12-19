/*
 * Nombre del archivo: Autor.java
 * Descripción: Clase Autor de la aplicación PantysMelRep.
 * Autor: Pan TyS Mel SA
 */
package PantysMelRep.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ReservaId implements Serializable {

    @Column(name="usuario_id")
    private String usuarioId;

    @Column(name="titulo_id")
    private String tituloId;

    public ReservaId() {
    }

    public ReservaId(String usuarioId, String tituloId) {
        this.usuarioId = usuarioId;
        this.tituloId = tituloId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getTituloId() {
        return tituloId;
    }

    public void setTituloId(String tituloId) {
        this.tituloId = tituloId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservaId reservaId = (ReservaId) o;
        return Objects.equals(usuarioId, reservaId.usuarioId) &&
                Objects.equals(tituloId, reservaId.tituloId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, tituloId);
    }
}
