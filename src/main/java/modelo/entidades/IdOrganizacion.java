/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.entidades;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author azul-
 */
@Embeddable
public class IdOrganizacion implements Serializable {
    private String rfc;
    private String correo_auditor;
    
    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCorreo() {
        return correo_auditor;
    }

    public void setCorreo(String correo) {
        this.correo_auditor = correo;
    }
    
    public IdOrganizacion() {
    }
    
     @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.rfc);
        hash = 59 * hash + Objects.hashCode(this.correo_auditor);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IdOrganizacion other = (IdOrganizacion) obj;
        if (!Objects.equals(this.rfc, other.rfc)) {
            return false;
        }
        if (!Objects.equals(this.correo_auditor, other.correo_auditor)) {
            return false;
        }
        return true;
    }
    
}
