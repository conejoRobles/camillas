package com.ubb.testing.tdd.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Paciente {

    @Id
    @GeneratedValue
    private int id;
    private String rut;
    private String nombre;
    private String apellido;
    private String estado;

    public Paciente(int id, String rut, String nombre, String apellido, String estado) {
        this.id = id;
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.estado = estado;
    }

    public Paciente() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "id=" + id +
                ", rut='" + rut + '\'' +
                ", apellido='" + apellido + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
