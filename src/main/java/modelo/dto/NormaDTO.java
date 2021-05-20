package modelo.dto;

import modelo.entidades.Norma;

public class NormaDTO {
    
    private Norma entidad;

    public NormaDTO() {
        entidad = new Norma();
    }

    public NormaDTO(Norma entidad) {
        this.entidad = entidad;
    }

    public Norma getEntidad() {
        return entidad;
    }

    public void setEntidad(Norma entidad) {
        this.entidad = entidad;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("Clave: ").append(getEntidad().getClave()).append("\n");
        sb.append("Nombre: ").append(getEntidad().getNombre()).append("\n");
        return sb.toString();        
    }
    
    
}
