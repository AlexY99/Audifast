/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dto;

import modelo.entidades.ContactoAuditoria;

/**
 *
 * @author azul-
 */
public class ContactoAuditoriaDTO {
    
    private ContactoAuditoria entidad;

    public ContactoAuditoriaDTO() {
        entidad = new ContactoAuditoria();
    }
    
    public ContactoAuditoriaDTO(ContactoAuditoria entidad) {
        this.entidad = entidad;
    }

    public ContactoAuditoria getEntidad() {
        return entidad;
    }

    public void setEntidad(ContactoAuditoria entidad) {
        this.entidad = entidad;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("Correo: ").append(getEntidad().getId().getCorreo()).append("\n");
        sb.append("Id Auditoria: ").append(getEntidad().getId().getIdAuditoria()).append("\n");
        sb.append("Puesto: ").append(getEntidad().getPuesto()).append("\n");
        sb.append("Nombre: ").append(getEntidad().getNombre()).append("\n");
        sb.append("Telefono: ").append(getEntidad().getTelefono()).append("\n");
        return sb.toString();        
    }
    
}
