/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author azul-
 */
@Entity
@Table (name = "clave_acceso", schema = "public")
public class Clave_Acceso implements Serializable {
    @Id
    private String clave;
    private String correo;
    private String url_plan;
    @ManyToOne
    @JoinColumn(name = "idAuditoria", referencedColumnName = "id")
    private Auditoria auditoria;

    public Clave_Acceso(){
        auditoria = new Auditoria();
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }   
    
}
