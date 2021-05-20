package modelo.dto;

import modelo.entidades.PlantillaAuditor;

public class PlantillaAuditorDTO {
    
    private PlantillaAuditor entidad;

    public PlantillaAuditorDTO() {
        entidad = new PlantillaAuditor();
    }

    public PlantillaAuditorDTO(PlantillaAuditor entidad) {
        this.entidad = entidad;
    }

    public PlantillaAuditor getEntidad() {
        return entidad;
    }

    public void setEntidad(PlantillaAuditor entidad) {
        this.entidad = entidad;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("ID: ").append(getEntidad().getId()).append("\n");
        sb.append("Nombre: ").append(getEntidad().getNombre()).append("\n");
        sb.append("Correo Auditor: ").append(getEntidad().getAuditor().getCorreo()).append("\n");
        return sb.toString();        
    }
    
    
}
