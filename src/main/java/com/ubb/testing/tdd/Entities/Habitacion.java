package com.ubb.testing.tdd.Entities;

public class Habitacion {

    private long id;
    private String especialidad;
    private String estado;
    private int nroCamasMax;

    public Habitacion(long id, String especialidad, String estado, int nroCamasMax) {
        this.id = id;
        this.especialidad = especialidad;
        this.estado = estado;
        this.nroCamasMax = nroCamasMax;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getNroCamasMax() {
        return nroCamasMax;
    }

    public void setNroCamasMax(int nroCamasMax) {
        this.nroCamasMax = nroCamasMax;
    }

    public Habitacion() {
    }
}
