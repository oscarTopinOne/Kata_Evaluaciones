package com.kata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kata.service.ConfluenceService;
import com.kata.service.EvaluacionService;

@RestController
@RequestMapping("/api/confluence")
public class ConfluenceController {

    @Autowired
    private ConfluenceService confluenceService;

    @Autowired
    private EvaluacionService evaluacionService;

    @PostMapping("/publicar")
    public ResponseEntity<?> publicarRankingReal() {
        String titulo = "📊 Reporte de Evaluaciones - Kata";
        String contenidoHtml = evaluacionService.generarHtmlRankingPorParticipante();
        confluenceService.publicarEnConfluence(titulo, contenidoHtml);
        return ResponseEntity.ok("✅ Ranking real publicado en Confluence.");
    }
    @PostMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("✅ Estoy vivo");
    }
}




