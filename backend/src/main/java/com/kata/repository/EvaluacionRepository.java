package com.kata.repository;

import com.kata.model.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {
}
