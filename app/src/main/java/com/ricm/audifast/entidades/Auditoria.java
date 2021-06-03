package com.ricm.audifast.entidades;

public class Auditoria {
    private int id;
    private String fecha_registro;
    private String organizacion;
    private String correo_auditor_lider;

    public Auditoria(int id, String correo_auditor_lider, String organizacion, String fecha_registro) {
        this.id = id;
        this.fecha_registro = fecha_registro;
        this.organizacion = organizacion;
        this.correo_auditor_lider = correo_auditor_lider;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(String organizacion) {
        this.organizacion = organizacion;
    }

    public String getCorreo_auditor_lider() {
        return correo_auditor_lider;
    }

    public void setCorreo_auditor_lider(String correo_auditor_lider) {
        this.correo_auditor_lider = correo_auditor_lider;
    }

    @Override
    public String toString() {
        return "Auditoria{" +
                "id=" + id +
                ", fecha_registro='" + fecha_registro + '\'' +
                ", organizacion='" + organizacion + '\'' +
                ", correo_auditor_lider='" + correo_auditor_lider + '\'' +
                '}';
    }
}
