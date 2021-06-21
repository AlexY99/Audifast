/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dto;

import modelo.entidades.Clave_Acceso;

/**
 *
 * @author azul-
 */
public class ClaveAccesoDTO {
    
    private Clave_Acceso entidad;

    public ClaveAccesoDTO(){
        entidad = new Clave_Acceso();
    }

    public ClaveAccesoDTO(Clave_Acceso entidad) {
        this.entidad = entidad;
    }

    public Clave_Acceso getEntidad() {
        return entidad;
    }

    public void setEntidad(Clave_Acceso entidad) {
        this.entidad = entidad;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("Correo: ").append(getEntidad().getCorreo()).append("\n");
        sb.append("Clave: ").append(getEntidad().getClave()).append("\n");
        sb.append("IdAuditoria: ").append(getEntidad().getAuditoria().getId()).append("\n");
        return sb.toString();        
    }
    
    
    
}
