package modelo.dto;

import modelo.entidades.Auditoria;

public class AuditoriaDTO {
    
    private Auditoria entidad;
    
    public AuditoriaDTO(){
        entidad = new Auditoria();
    }
    
    public AuditoriaDTO(Auditoria entidad) {
        this.entidad = entidad;
    }

    public Auditoria getEntidad() {
        return entidad;
    }

    public void setEntidad(Auditoria entidad) {
        this.entidad = entidad;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("ID: ").append(getEntidad().getId()).append("\n");
        sb.append("RFC de la organizacion: ").append(getEntidad().getRfc_organizacion()).append("\n");
        sb.append("Correo del auditor lider: ").append(getEntidad().getCorreo_auditor_lider()).append("\n");
        sb.append("Fecha Registro: ").append(getEntidad().getFecha_registro()).append("\n");
        return sb.toString();
    }
}
