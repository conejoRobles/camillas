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
}
