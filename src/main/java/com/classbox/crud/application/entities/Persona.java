package com.classbox.crud.application.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "personas")
public class Persona implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int dni;
    private String nombre;
    private String apellido;
    private int edad;
    private String imagen;
    
    public Persona() {}
    
    public Persona(int dni, String nombre, String apellido, int edad, String imagen) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.imagen = imagen;
    }
    
    public int getDni() {
        return dni;
    }
    
    public void setDni(int dni) {
        this.dni = dni;
    }
    
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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
    
    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
    @Override
    public String toString() {
        return "Persona{" + "DNI=" + dni + ", nombre=" + nombre + ", apellido=" + apellido + ", edad=" + edad + '}';
    }
}
