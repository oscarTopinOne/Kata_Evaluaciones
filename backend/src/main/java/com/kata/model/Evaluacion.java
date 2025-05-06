package com.kata.model;

import javax.persistence.*;

@Entity
public class Evaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Participante participante;

    @ManyToOne
    private Evaluador evaluador;

    private double perfil;
    private double comunicacion;
    private double tecnico;
    private double puntosExtra;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Participante getParticipante() { return participante; }
    public void setParticipante(Participante participante) { this.participante = participante; }

    public Evaluador getEvaluador() { return evaluador; }
    public void setEvaluador(Evaluador evaluador) { this.evaluador = evaluador; }

    public double getPerfil() { return perfil; }
    public void setPerfil(double perfil) { this.perfil = perfil; }

    public double getComunicacion() { return comunicacion; }
    public void setComunicacion(double comunicacion) { this.comunicacion = comunicacion; }

    public double getTecnico() { return tecnico; }
    public void setTecnico(double tecnico) { this.tecnico = tecnico; }

    public double getPuntosExtra() { return puntosExtra; }
    public void setPuntosExtra(double puntosExtra) { this.puntosExtra = puntosExtra; }
}
