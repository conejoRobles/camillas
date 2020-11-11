package com.ubb.testing.tdd.Entities;

import javax.persistence.*;

@Entity
public class Piso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String estado;

    private int nroHabitaciones;

    public Piso() {
    }

    public Piso(String nombre, String estado, int nroHabitaciones) {
        this.nombre = nombre;
        this.estado = estado;
        this.nroHabitaciones = nroHabitaciones;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
