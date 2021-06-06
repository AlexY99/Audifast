package modelo.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "proceso_acta", schema = "public")
public class ProcesoActa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private float ponderacion;
    private float resultado ;
    private String observaciones;
    
    @ManyToOne
    @JoinColumn(name = "idAuditoria", referencedColumnName = "id")
    private Auditoria auditoria;
    
    @OneToOne
    @JoinColumn(name = "idProceso", referencedColumnName = "id")
    private Proceso proceso;
    
    @OneToOne
    @JoinColumn(name = "correo_encargado", referencedColumnName = "correo")
    private Auditor auditor;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "procesoActa")
    private List<RequisitoActa> requisitosActa;

    public ProcesoActa() {
        auditoria = new Auditoria();
        proceso = new Proceso();
        auditor = new Auditor();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public float getPonderacion() {
        return ponderacion;
    }

    public void setPonderacion(float ponderacion) {
        this.ponderacion = ponderacion;
    }

    public float getResultado() {
        return resultado;
    }

    public void setResultado(float resultado) {
        this.resultado = resultado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }

    public Proceso getProceso() {
        return proceso;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    public Auditor getAuditor() {
        return auditor;
    }

    public void setAuditor(Auditor auditor) {
        this.auditor = auditor;
    }

    public List<RequisitoActa> getRequisitosActa() {
        return requisitosActa;
    }

    public void setRequisitosActa(List<RequisitoActa> requisitosActa) {
        this.requisitosActa = requisitosActa;
    }
    
}
