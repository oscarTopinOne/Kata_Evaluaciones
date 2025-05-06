package com.kata.service;

import javax.transaction.Transactional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabaseCleanerService {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseCleanerService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void clean() {
        jdbcTemplate.execute("TRUNCATE TABLE evaluacion RESTART IDENTITY CASCADE");
        //jdbcTemplate.execute("TRUNCATE TABLE participante RESTART IDENTITY CASCADE");
        //jdbcTemplate.execute("TRUNCATE TABLE evaluador RESTART IDENTITY CASCADE");
    }
}
