/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
/**
 *
 * @author azul-
 */
@Entity
@Table (name = "organizacion", schema = "public")
public class Organizacion implements Serializable{
       
    @EmbeddedId
    private IdOrganizacion Id;
    private String nombre;
    private String giro;
    @Column(name = "direccion_operacion", nullable = false, length = 50)
    private String direccionO; //Direccion Operacional
    @Column(name = "direccion_fiscal", nullable = false, length = 50)
    private String direccionF; //Direccion Fiscal


    public IdOrganizacion getId() {
        return Id;
    }
     
    public void setId(IdOrganizacion Id) {
        this.Id = Id;
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
       
}


