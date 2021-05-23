package modelo.entidades;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table (name = "contacto_auditoria", schema = "public")
public class ContactoAuditoria implements Serializable {
    
    @EmbeddedId
    private IdContactoAuditoria id;
    private String puesto;
    private String nombre;
    private String telefono;
    @ManyToOne
    @JoinColumn(name="idAuditoria")
    @MapsId("idAuditoria")
    private Auditoria auditoria;

    public ContactoAuditoria() {
        id = new IdContactoAuditoria();
    }
       
    public IdContactoAuditoria getId() {
        return id;
    }

    public void setId(IdContactoAuditoria id) {
        this.id = id;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }   
    
}
