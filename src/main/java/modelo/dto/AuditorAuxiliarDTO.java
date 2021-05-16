/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dto;

import modelo.entidades.AuditorAuxiliar;

/**
 *
 * @author azul-
 */
public class AuditorAuxiliarDTO {

    private AuditorAuxiliar entidad;
    
    public AuditorAuxiliarDTO(){
        entidad = new AuditorAuxiliar();
    }
    
    public AuditorAuxiliarDTO(AuditorAuxiliar entidad) {
        this.entidad = entidad;
    }

    public AuditorAuxiliar getEntidad() {
        return entidad;
    }

    public void setEntidad(AuditorAuxiliar entidad) {
        this.entidad = entidad;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("Correo: ").append(getEntidad().getId().getCorreo()).append("\n");
        sb.append("Id Auditoria: ").append(getEntidad().getId().getIdAuditoria()).append("\n");
        return sb.toString();        
    }
    
    
    
}
