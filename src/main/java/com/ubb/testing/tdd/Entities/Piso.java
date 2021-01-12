package com.ubb.testing.tdd.Entities;

import javax.persistence.*;

@Entity
public class Piso {

    @Id
    @GeneratedValue
    private Integer id;

    private String nombre;

    private String estado;

    private int nroHabitaciones;

    public Piso() {
    }

    public Piso(Integer id, String nombre, String estado, int nroHabitaciones) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
        this.nroHabitaciones = nroHabitaciones;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getNroHabitaciones() {
        return nroHabitaciones;
    }

    public void setNroHabitaciones(int nroHabitaciones) {
        this.nroHabitaciones = nroHabitaciones;
    }
}
