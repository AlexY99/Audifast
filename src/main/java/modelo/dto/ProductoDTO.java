/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dto;

import modelo.entidades.Producto;

/**
 *
 * @author azul-
 */
public class ProductoDTO {
    
    private Producto entidad;

    public ProductoDTO() {
        entidad = new Producto();
    }

    public ProductoDTO(Producto entidad) {
        this.entidad = entidad;
    }

    public Producto getEntidad() {
        return entidad;
    }

    public void setEntidad(Producto entidad) {
        this.entidad = entidad;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("Clave: ").append(getEntidad().getId().getClave()).append("\n");
        sb.append("Id Auditoria: ").append(getEntidad().getId().getIdAuditoria()).append("\n");
        sb.append("Descripcion: ").append(getEntidad().getDescripcion()).append("\n");
        return sb.toString();        
    }
    
    
}
