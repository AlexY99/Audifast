package com.ricm.audifast.entidades;

public class Requisito {
    int id;
    String descripcion;
    String clave_norma;
    int cumplimiento;

    public Requisito(int id, String descripcion, String clave_norma, int cumplimiento) {
        this.id = id;
        this.descripcion = descripcion;
        this.clave_norma = clave_norma;
        this.cumplimiento = cumplimiento;
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

    public String getClave_norma() {
        return clave_norma;
    }

    public void setClave_norma(String clave_norma) {
        this.clave_norma = clave_norma;
    }

    public int getCumplimiento() {
        return cumplimiento;
    }

    public void setCumplimiento(int cumplimiento) {
        this.cumplimiento = cumplimiento;
    }

    @Override
    public String toString() {
        return "Requisito{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", clave_norma='" + clave_norma + '\'' +
                ", cumplimiento=" + cumplimiento +
                '}';
    }
}
