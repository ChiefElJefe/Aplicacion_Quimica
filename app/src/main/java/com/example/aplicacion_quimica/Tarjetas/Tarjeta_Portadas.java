package com.example.aplicacion_quimica.Tarjetas;

public class Tarjeta_Portadas {
    private String titulo;
    private int imagen;

    public Tarjeta_Portadas(String titulo, int imagen) {
        this.titulo = titulo;
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
