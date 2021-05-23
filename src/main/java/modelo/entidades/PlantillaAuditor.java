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
import javax.persistence.Table;

@Entity
@Table(name = "plantilla_auditor")
public class PlantillaAuditor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String nombre;
    
    @ManyToOne
    @JoinColumn(name = "correo_auditor", referencedColumnName = "correo")
    private Auditor auditor;
    
    @OneToMany(mappedBy = "plantilla", cascade = CascadeType.ALL)
    private List<Proceso> procesos;

    public PlantillaAuditor() {
    }

    public PlantillaAuditor(Integer id) {
        this.id = id;
    }

    public PlantillaAuditor(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Auditor getAuditor() {
        return auditor;
    }

    public void setAuditor(Auditor auditor) {
        this.auditor = auditor;
    }

    public List<Proceso> getProcesos() {
        return procesos;
    }

    public void setProcesoList(List<Proceso> procesos) {
        this.procesos = procesos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlantillaAuditor)) {
            return false;
        }
        PlantillaAuditor other = (PlantillaAuditor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entidades.PlantillaAuditor[ id=" + id + " ]";
    }
    
}
