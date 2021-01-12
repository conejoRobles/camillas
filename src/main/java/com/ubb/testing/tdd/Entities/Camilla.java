package com.ubb.testing.tdd.Entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Camilla {

    @Id
    @GeneratedValue
    private int id;
    private String tipo;
    private String estado;
    private int year;

    public Camilla() {
    }

    public Camilla(String tipo, String estado, int year) {
        this.tipo = tipo;
        this.estado = estado;
        this.year = year;
    }

    public Camilla(int id, String tipo, String estado, int year) {
        this.id = id;
        this.tipo = tipo;
        this.estado = estado;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Camilla{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", estado='" + estado + '\'' +
                ", year=" + year +
                '}';
    }
}
