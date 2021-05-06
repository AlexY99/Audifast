package modelo.dto;

import modelo.entidades.Auditor;

public class AuditorDTO {
    private Auditor entidad;
    
    public AuditorDTO(){
        entidad = new Auditor();
    }
    
    public AuditorDTO(Auditor entidad) {
        this.entidad = entidad;
    }

    public Auditor getEntidad() {
        return entidad;
    }

    public void setEntidad(Auditor entidad) {
        this.entidad = entidad;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("nombre").append(getEntidad().getNombre()).append("\n");
        sb.append("correo").append(getEntidad().getCorreo()).append("\n");
        sb.append("pswd").append(getEntidad().getPswd()).append("\n");
        sb.append("telefono").append(getEntidad().getTelefono()).append("\n");
        return sb.toString();
    }
}
