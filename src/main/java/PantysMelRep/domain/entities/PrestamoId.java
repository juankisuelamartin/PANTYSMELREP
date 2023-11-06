package PantysMelRep.domain.entities;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PrestamoId implements Serializable {

    private String usuarioId;
    private String tituloId;

    public PrestamoId() {
    }

    public PrestamoId(String usuarioId, String tituloId) {
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
        PrestamoId that = (PrestamoId) o;
        return Objects.equals(usuarioId, that.usuarioId) &&
                Objects.equals(tituloId, that.tituloId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, tituloId);
    }

}