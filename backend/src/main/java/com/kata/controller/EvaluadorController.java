package com.kata.controller;

import com.kata.model.Evaluador;
import com.kata.repository.EvaluadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluadores")
@CrossOrigin("*")
public class EvaluadorController {

    @Autowired
    private EvaluadorRepository evaluadorRepository;

    @PostMapping
    public Evaluador crearEvaluador(@RequestBody Evaluador evaluador) {
        return evaluadorRepository.save(evaluador);
    }

    @GetMapping
    public List<Evaluador> listarEvaluadores() {
        return evaluadorRepository.findAll();
    }
}
