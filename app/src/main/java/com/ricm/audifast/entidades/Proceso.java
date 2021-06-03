package com.ricm.audifast.entidades;

public class Proceso {
    int id;
    String descripcion;
    Double ponderacion;
    String encargado;
    String correo_encargado;

    public Proceso(int id, String descripcion, Double ponderacion, String encargado, String correo_encargado) {
        this.id = id;
        this.descripcion = descripcion;
        this.ponderacion = ponderacion;
        this.encargado = encargado;
        this.correo_encargado = correo_encargado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPonderacion() {
        return ponderacion;
    }

    public void setPonderacion(Double ponderacion) {
        this.ponderacion = ponderacion;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public String getCorreo_encargado() {
        return correo_encargado;
    }

    public void setCorreo_encargado(String correo_encargado) {
        this.correo_encargado = correo_encargado;
    }

    @Override
    public String toString() {
        return "Proceso{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", ponderacion=" + ponderacion +
                ", encargado='" + encargado + '\'' +
                ", correo_encargado='" + correo_encargado + '\'' +
                '}';
    }
}
