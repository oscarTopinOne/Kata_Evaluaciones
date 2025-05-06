package com.kata.controller;

import com.kata.model.Participante;
import com.kata.repository.ParticipanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participantes")
public class ParticipanteController {

    @Autowired
    private ParticipanteRepository participanteRepository;

    @PostMapping
    public Participante crearParticipante(@RequestBody Participante participante) {
        return participanteRepository.save(participante);
    }

    @GetMapping
    public List<Participante> listarParticipantes() {
        return participanteRepository.findAll();
    }
}

