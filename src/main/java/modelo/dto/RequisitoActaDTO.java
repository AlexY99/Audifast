/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dto;

import modelo.entidades.RequisitoActa;

/**
 *
 * @author azul-
 */
public class RequisitoActaDTO {

    private RequisitoActa entidad;

    public RequisitoActaDTO(RequisitoActa entidad) {
        this.entidad = entidad;
    }
    
    public RequisitoActaDTO(){
        entidad = new RequisitoActa();
    }

    public RequisitoActa getEntidad() {
        return entidad;
    }

    public void setEntidad(RequisitoActa entidad) {
        this.entidad = entidad;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("ID: ").append(getEntidad().getId()).append("\n");
        sb.append("idProcesoActa: ").append(getEntidad().getProcesoActa().getId()).append("\n");
        sb.append("idRequisito: ").append(getEntidad().getRequisito().getId()).append("\n");
        sb.append("Cumplimiento: ").append(getEntidad().getCumplimiento()).append("\n");
        return sb.toString();        
    }

}
