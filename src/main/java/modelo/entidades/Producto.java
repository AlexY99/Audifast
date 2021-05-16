/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.entidades;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

/**
 *
 * @author azul-
 */
@Entity
@Table (name = "producto", schema = "public")
public class Producto implements Serializable {
    
    @EmbeddedId
    private IdProducto id;
    private String descripcion;
    
    @ManyToOne
    @JoinColumn(name="idAuditoria")
    @MapsId("idAuditoria")
    private Auditoria auditoria;

    public Producto() {
       
        this.id = new IdProducto();
    
    }
    
    public IdProducto getId() {
        return id;
    }

    public void setId(IdProducto id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }
    
}
