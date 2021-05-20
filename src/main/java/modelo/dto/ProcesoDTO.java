package modelo.dto;

import modelo.entidades.Proceso;

public class ProcesoDTO {
    
    private Proceso entidad;

    public ProcesoDTO() {
        entidad = new Proceso();
    }

    public ProcesoDTO(Proceso entidad) {
        this.entidad = entidad;
    }

    public Proceso getEntidad() {
        return entidad;
    }

    public void setEntidad(Proceso entidad) {
        this.entidad = entidad;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("ID: ").append(getEntidad().getId()).append("\n");
        sb.append("IDPlantilla: ").append(getEntidad().getPlantilla().getId()).append("\n");
        sb.append("Descripcion: ").append(getEntidad().getDescripcion()).append("\n");
        return sb.toString();        
    }
    
    
}
