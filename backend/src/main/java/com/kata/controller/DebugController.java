package com.kata.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kata.service.DatabaseCleanerService;

@RestController
@RequestMapping("/api/debug")
public class DebugController {

    private final DatabaseCleanerService service;

    public DebugController(DatabaseCleanerService service) {
        this.service = service;
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetDB() {
        service.clean();
        return ResponseEntity.ok("Base de datos reiniciada.");
    }
}

