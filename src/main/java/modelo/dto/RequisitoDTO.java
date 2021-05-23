package modelo.dto;

import modelo.entidades.Requisito;

public class RequisitoDTO {
    
    private Requisito entidad;

    public RequisitoDTO() {
        entidad = new Requisito();
    }

    public RequisitoDTO(Requisito entidad) {
        this.entidad = entidad;
    }

    public Requisito getEntidad() {
        return entidad;
    }

    public void setEntidad(Requisito entidad) {
        this.entidad = entidad;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("ID: ").append(getEntidad().getId()).append("\n");
        sb.append("ClaveNorma: ").append(getEntidad().getClave_norma()).append("\n");
        sb.append("Descripcion: ").append(getEntidad().getDescripcion()).append("\n");
        sb.append("IDProceso: ").append(getEntidad().getProceso().getId()).append("\n");
        return sb.toString();        
    }
    
    
}
