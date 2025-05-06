package com.kata.controller;

import com.kata.dto.EvaluacionDTO;
import com.kata.dto.RankingDTO;
import com.kata.model.*;
import com.kata.repository.*;
import com.kata.service.EvaluacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/evaluaciones")
public class EvaluacionController {

    @Autowired
    private EvaluacionRepository evaluacionRepository;

    @Autowired
    private ParticipanteRepository participanteRepository;

    @Autowired
    private EvaluadorRepository evaluadorRepository;

    @Autowired
    private EvaluacionService evaluacionService;

    @PostMapping
    public ResponseEntity<?> registrarEvaluacion(@RequestBody EvaluacionDTO dto) {
        System.out.println("Entró al controlador");
        try {
            Participante p = participanteRepository.findById(dto.participanteId)
                .orElseThrow(() -> new RuntimeException("Participante no encontrado con ID: " + dto.participanteId));
        
            Evaluador j = evaluadorRepository.findById(dto.juradoId)
                .orElseThrow(() -> new RuntimeException("Evaluador no encontrado con ID: " + dto.juradoId));

            Evaluacion e = new Evaluacion();
            e.setParticipante(p);
            e.setEvaluador(j);
            e.setPerfil(dto.perfil);
            e.setComunicacion(dto.comunicacion);
            e.setTecnico(dto.tecnico);
            e.setPuntosExtra(dto.puntosExtra);

            Evaluacion guardada = evaluacionRepository.save(e);
            return ResponseEntity.ok(guardada);

        } catch (Exception ex) {
            ex.printStackTrace(); // Muestra el error en la consola para debug
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al registrar evaluación: " + ex.getMessage());
        }
    }


    @GetMapping("/resultados")
    public List<Map<String, Object>> resultados() {
        List<Evaluacion> evaluaciones = evaluacionRepository.findAll();
        Map<Long, List<Evaluacion>> porParticipante = new HashMap<>();

        for (Evaluacion e : evaluaciones) {
            porParticipante
                .computeIfAbsent(e.getParticipante().getId(), k -> new ArrayList<>())
                .add(e);
        }

        List<Map<String, Object>> resultado = new ArrayList<>();
        for (Map.Entry<Long, List<Evaluacion>> entry : porParticipante.entrySet()) {
            double total = 0;
            int count = entry.getValue().size();
            for (Evaluacion e : entry.getValue()) {
                double score = e.getPerfil() * 0.10 + e.getComunicacion() * 0.35 + e.getTecnico() * 0.55 + e.getPuntosExtra();
                total += score;
            }
            double promedio = total / count;
            Map<String, Object> r = new HashMap<>();
            r.put("participanteId", entry.getKey());
            r.put("puntaje", promedio);
            r.put("aprobado", promedio >= 75.0);
            resultado.add(r);
        }

        resultado.sort((a, b) -> Double.compare((Double)b.get("puntaje"), (Double)a.get("puntaje")));
        return resultado;
    }

    @GetMapping("/ranking")
    public List<RankingDTO> obtenerRanking() {
        return evaluacionService.generarRankingPorParticipante();
    }
}
