package com.kata.dto;

public class RankingDTO {
    private String nombre;
    private double puntaje;
    private boolean aprobado;
    private int posicion;

    public RankingDTO(String nombre, double puntaje, boolean aprobado, int posicion) {
        this.nombre = nombre;
        this.puntaje = puntaje;
        this.aprobado = aprobado;
        this.posicion = posicion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(double puntaje) {
        this.puntaje = puntaje;
    }

    public boolean isAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }

    public int getPosicion() {
        return posicion;
    }
}
