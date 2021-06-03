package com.ricm.audifast.entidades;

public class AuditorAuxiliar {
    private String correo;
    private String nombre;
    private String telefono;

    public AuditorAuxiliar(String correo, String nombre, String telefono) {
        this.correo = correo;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Contacto{" +
                "correo='" + correo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
