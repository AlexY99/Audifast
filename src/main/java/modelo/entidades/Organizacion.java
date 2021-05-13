
package modelo.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.OneToMany;

@Entity
@Table (name = "organizacion", schema = "public")
public class Organizacion implements Serializable{
       
    @EmbeddedId
    private IdOrganizacion id;
    private String nombre;
    private String giro;
    @Column(name = "direccion_operacion", nullable = false, length = 50)
    private String direccionO; //Direccion Operacional
    @Column(name = "direccion_fiscal", nullable = false, length = 50)
    private String direccionF; //Direccion Fiscal
    
    @OneToMany(mappedBy="organizacion")
    List<Auditoria> auditorias = new ArrayList<Auditoria>();

    public IdOrganizacion getId() {
        return id;
    }
     
    public void setId(IdOrganizacion id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGiro() {
        return giro;
    }

    public void setGiro(String giro) {
        this.giro = giro;
    }

    public String getDireccionO() {
        return direccionO;
    }

    public void setDireccionO(String direccionO) {
        this.direccionO = direccionO;
    }

    public String getDireccionF() {
        return direccionF;
    }

    public void setDireccionF(String direccionF) {
        this.direccionF = direccionF;
    }

    public List<Auditoria> getAuditorias() {
        return auditorias;
    }

    public void setAuditorias(List<Auditoria> auditorias) {
        this.auditorias = auditorias;
    }
       
}


