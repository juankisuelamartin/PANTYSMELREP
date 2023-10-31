package PantysMelRep.domain.entities;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

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

}