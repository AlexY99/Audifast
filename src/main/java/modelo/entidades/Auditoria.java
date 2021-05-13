package modelo.entidades;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;

@Entity
@Table (name = "Auditoria", schema = "public")
public class Auditoria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String rfc_organizacion;
    private String correo_auditor_lider;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_registro;
    
    @OneToMany(mappedBy="auditoria")
    private List<AuditorAuxiliar> auditoresAuxiliares = new ArrayList<AuditorAuxiliar>();

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name="rfc_organizacion",referencedColumnName = "rfc"),
        @JoinColumn(name="correo_auditor_lider", referencedColumnName = "correo_auditor")
    })
    @MapsId("id")
    private Organizacion organizacion;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRfc_organizacion() {
        return rfc_organizacion;
    }

    public void setRfc_organizacion(String rfc_organizacion) {
        this.rfc_organizacion = rfc_organizacion;
    }

    public String getCorreo_auditor_lider() {
        return correo_auditor_lider;
    }

    public void setCorreo_auditor_lider(String correo_auditor_lider) {
        this.correo_auditor_lider = correo_auditor_lider;
    }
    
    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public List<AuditorAuxiliar> getAuditoresAuxiliares() {
        return auditoresAuxiliares;
    }

    public void setAuditoresAuxiliares(List<AuditorAuxiliar> auditoresAuxiliares) {
        this.auditoresAuxiliares = auditoresAuxiliares;
    }

    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }
    
    public String fecha(){
        SimpleDateFormat datetimeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return datetimeformat.format(this.fecha_registro);
    }
        
}
