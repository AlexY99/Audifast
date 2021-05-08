/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dto;

import modelo.entidades.Organizacion;

/**
 *
 * @author azul-
 */
public class EmpresaDTO {
    
    private Organizacion entidad;
    
    public EmpresaDTO(){
        entidad = new Organizacion();
    }
    
    public EmpresaDTO(Organizacion entidad) {
        this.entidad = entidad;
    }

    public Organizacion getEntidad() {
        return entidad;
    }

    public void setEntidad(Organizacion entidad) {
        this.entidad = entidad;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("Nombre: ").append(getEntidad().getNombre()).append("\n");
        sb.append("RFC de la organizacion: ").append(getEntidad().getId().getRfc()).append("\n");
        sb.append("Correo Auditor: ").append(getEntidad().getId().getCorreo()).append("\n");
        sb.append("Giro: ").append(getEntidad().getGiro()).append("\n");
        sb.append("Dirección Fiscal: ").append(getEntidad().getDireccionF()).append("\n");
        sb.append("Dirección Operacional: ").append(getEntidad().getDireccionO()).append("\n");
        return sb.toString();
    }
    
}
