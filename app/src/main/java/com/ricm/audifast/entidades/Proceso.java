package com.ricm.audifast.entidades;

public class Proceso {
    int id;
    String descripcion;
    double ponderacion;
    String encargado;
    String observaciones;
    boolean evaluado;
    String correo_encargado;
    double resultado;

    public Proceso(int id, String descripcion, Double ponderacion, String encargado, String correo_encargado, boolean evaluado, String observaciones,double resultado) {
        this.id = id;
        this.descripcion = descripcion;
        this.ponderacion = ponderacion;
        this.encargado = encargado;
        this.correo_encargado = correo_encargado;
        this.evaluado = evaluado;
        this.observaciones = observaciones;
        this.resultado = resultado;
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

    public double getPonderacion() {
        return ponderacion;
    }

    public void setPonderacion(double ponderacion) {
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

    public boolean isEvaluado() {
        return evaluado;
    }

    public void setEvaluado(boolean evaluado) {
        this.evaluado = evaluado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public double getResultado() {
        return resultado;
    }

    public void setResultado(double resultado) {
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return "Proceso{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", ponderacion=" + ponderacion +
                ", encargado='" + encargado + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", correo_encargado='" + correo_encargado + '\'' +
                ", resultado=" + resultado +
                '}';
    }
}
