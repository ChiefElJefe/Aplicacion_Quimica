package com.example.aplicacion_quimica.clases;

public class Sustancia {
    private String sustancia;
    private double mr;

    public Sustancia(String sustancia, double mr) {
        this.sustancia = sustancia;
        this.mr = mr;
    }

    public String getSustancia() {
        return sustancia;
    }

    public void setSustancia(String sustancia) {
        this.sustancia = sustancia;
    }

    public double getMr() {
        return mr;
    }

    public void setMr(double mr) {
        this.mr = mr;
    }
}
