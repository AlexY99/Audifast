package modelo.entidades;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

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
    
    public String fecha(){
        SimpleDateFormat datetimeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return datetimeformat.format(this.fecha_registro);
    }
        
}
