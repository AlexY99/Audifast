package modelo.entidades;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name = "Auditor", schema = "public")
public class Auditor{
    @Id
    private String correo;
    private String nombre;
    private String pswd;
    private String telefono;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "auditor")
    private List<PlantillaAuditor> plantillasAuditor;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "auditor")
    private List<Norma> normas;

    public Auditor() {}
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<PlantillaAuditor> getPlantillasAuditor() {
        return plantillasAuditor;
    }

    public void setPlantillasAuditor(List<PlantillaAuditor> plantillasAuditor) {
        this.plantillasAuditor = plantillasAuditor;
    }
    
}
