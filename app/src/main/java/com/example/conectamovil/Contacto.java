package com.example.conectamovil;

public class Contacto {
    private String nombre;
    private String apellido;
    private String apodo;
    private String correoElectronico;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    // Constructor, getters y setters
    // ...

    public Contacto() {
        // Constructor vacío necesario para Firebase
    }

    public Contacto(String nombre, String apellido, String apodo, String correoElectronico) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.apodo = apodo;
        this.correoElectronico = correoElectronico;
    }
}

