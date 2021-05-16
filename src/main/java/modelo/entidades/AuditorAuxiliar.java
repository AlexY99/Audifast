package modelo.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
@Table (name = "auditor_auxiliar", schema = "public")
public class AuditorAuxiliar implements Serializable {

    @EmbeddedId
    private IdAuditorAuxiliar id;
   
    @ManyToOne
    @JoinColumn(name="idAuditoria")
    @MapsId("idAuditoria")
    private Auditoria auditoria;
    
    public AuditorAuxiliar() {
        id = new IdAuditorAuxiliar();
    }
    
    public IdAuditorAuxiliar getId() {
        return id;
    }

    public void setId(IdAuditorAuxiliar id) {
        this.id = id;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }
        
}
