package modelo.dto;

import modelo.entidades.ProcesoActa;


public class ProcesoActaDTO {
    private ProcesoActa entidad;

    public ProcesoActaDTO() {
        entidad = new ProcesoActa();
    }

    public ProcesoActaDTO(ProcesoActa entidad) {
        this.entidad = entidad;
    }

    public ProcesoActa getEntidad() {
        return entidad;
    }

    public void setEntidad(ProcesoActa entidad) {
        this.entidad = entidad;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("ID: ").append(getEntidad().getId()).append("\n");
        sb.append("IDAuditoria: ").append(getEntidad().getAuditoria().getId()).append("\n");
        sb.append("Correo Encargado: ").append(getEntidad().getAuditor().getCorreo()).append("\n");
        sb.append("IDProceso: ").append(getEntidad().getProceso().getId()).append("\n");
        sb.append("Ponderacion: ").append(getEntidad().getPonderacion()).append("\n");
        sb.append("Resultado: ").append(getEntidad().getResultado()).append("\n");
        sb.append("Observaciones: ").append(getEntidad().getObservaciones()).append("\n");
        return sb.toString();        
    }
}


