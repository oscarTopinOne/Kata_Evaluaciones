package com.kata.service;

import com.kata.dto.RankingDTO;
import com.kata.model.Evaluacion;
import com.kata.repository.EvaluacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class EvaluacionService {

    @Autowired
    private EvaluacionRepository evaluacionRepository;

    // Calcula el ranking promedio por participante a partir de varias evaluaciones
    public List<RankingDTO> generarRankingPorParticipante() {
        List<RankingDTO> rankingSinPosicion = evaluacionRepository.findAll().stream()
            .collect(Collectors.groupingBy(e -> e.getParticipante().getNombre()))
            .entrySet().stream()
            .map(entry -> {
                String nombre = entry.getKey();
                List<Evaluacion> evals = entry.getValue();

                double promedio = evals.stream()
                    .mapToDouble(e -> e.getPerfil() * 0.10 +
                                     e.getComunicacion() * 0.35 +
                                     e.getTecnico() * 0.55 +
                                     e.getPuntosExtra())
                    .average()
                    .orElse(0.0);

                boolean aprobado = promedio >= 75.0;
                System.out.println("Participante: " + nombre + " - Promedio: " + promedio + " - Aprobado: " + aprobado);
                return new RankingDTO(nombre, promedio, aprobado, 0); // Posición se agrega después
            })
            .sorted(Comparator.comparingDouble(RankingDTO::getPuntaje).reversed())
            .collect(Collectors.toList());

        // Asignar posición final del ranking
        return IntStream.range(0, rankingSinPosicion.size())
            .mapToObj(i -> {
                RankingDTO base = rankingSinPosicion.get(i);
                return new RankingDTO(
                    base.getNombre(),
                    base.getPuntaje(),
                    base.isAprobado(),
                    i + 1
                );
            })
            .collect(Collectors.toList());
    }

    public String generarHtmlRankingPorParticipante() {
        List<RankingDTO> ranking = generarRankingPorParticipante();

        StringBuilder html = new StringBuilder();
        html.append("<h1>\ud83c\udfc6 Ranking de Evaluaciones</h1>");
        html.append("<table><tbody>")
            .append("<tr>")
            .append("<th>Posición</th>")
            .append("<th>Participante</th>")
            .append("<th>Puntaje</th>")
            .append("<th>¿Aprueba?</th>")
            .append("</tr>");

        for (RankingDTO r : ranking) {
            String icono;
            switch (r.getPosicion()) {
                case 1: icono = "\ud83e\udd47"; break;
                case 2: icono = "\ud83e\udd48"; break;
                case 3: icono = "\ud83e\udd49"; break;
                default: icono = String.valueOf(r.getPosicion());
            }

            String estadoAprobacion = r.isAprobado() ? "Sí" : "No";

            html.append("<tr>")
                .append("<td>").append(icono).append("</td>")
                .append("<td>").append(r.getNombre()).append("</td>")
                .append("<td>").append(String.format("%.2f", r.getPuntaje())).append("</td>")
                .append("<td><p>").append(estadoAprobacion).append("</p></td>")
                .append("</tr>");
        }

        html.append("</tbody></table>");
        return html.toString();
    }
}

