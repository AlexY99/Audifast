package modelo.entidades;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class IdAuditorAuxiliar implements Serializable {
    private String correo_auditor;
    private int idAuditoria;

    public String getCorreo() {
        return correo_auditor;
    }

    public void setCorreo_auditor(String correo) {
        this.correo_auditor = correo;
    }

    public int getIdAuditoria() {
        return idAuditoria;
    }

    public void setIdAuditoria(int idAuditoria) {
        this.idAuditoria = idAuditoria;
    }
    
    public IdAuditorAuxiliar() {
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.correo_auditor);
        hash = 59 * hash + Objects.hashCode(this.idAuditoria);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IdAuditorAuxiliar other = (IdAuditorAuxiliar) obj;
        if (!Objects.equals(this.correo_auditor, other.correo_auditor)) {
            return false;
        }
        if (!Objects.equals(this.idAuditoria, other.idAuditoria)) {
            return false;
        }
        return true;
    }
    
}
