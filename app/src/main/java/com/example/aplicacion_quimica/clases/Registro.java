package com.example.aplicacion_quimica.clases;

public class Registro {
    private String sustancia, concentracion, masa, volumen;

    public Registro(String sustancia, String concentracion, String masa, String volumen) {
        this.sustancia = sustancia;
        this.concentracion = concentracion;
        this.masa = masa;
        this.volumen = volumen;
    }

    public String getSustancia() {
        return sustancia;
    }

    public void setSustancia(String sustancia) {
        this.sustancia = sustancia;
    }

    public String getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(String concentracion) {
        this.concentracion = concentracion;
    }

    public String getMasa() {
        return masa;
    }

    public void setMasa(String masa) {
        this.masa = masa;
    }

    public String getVolumen() {
        return volumen;
    }

    public void setVolumen(String volumen) {
        this.volumen = volumen;
    }
}
