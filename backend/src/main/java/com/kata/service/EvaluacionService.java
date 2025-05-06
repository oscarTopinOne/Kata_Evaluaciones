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
        html.append("<h1>🏆 Ranking de Evaluaciones</h1>");
        html.append("<table style='border-collapse: collapse; width: 100%;' border='1' cellpadding='5' cellspacing='0'>");
        html.append("<tr style='background-color: #f0f0f0; text-align: left;'>")
            .append("<th>Posición</th>")
            .append("<th>Participante</th>")
            .append("<th>Puntaje</th>")
            .append("<th>¿Aprueba?</th>")
            .append("</tr>");
    
        for (RankingDTO r : ranking) {
            String icono;
            String rowStyle = "";
    
            switch (r.getPosicion()) {
                case 1:
                    icono = "🥇";
                    rowStyle = "background-color: #fff8dc;";
                    break;
                case 2:
                    icono = "🥈";
                    rowStyle = "background-color: #e0e0e0;";
                    break;
                case 3:
                    icono = "🥉";
                    rowStyle = "background-color: #f5deb3;";
                    break;
                default:
                    icono = String.valueOf(r.getPosicion());
            }
    
            // Macro directamente en formato storage
            String macroStatus = r.isAprobado()
                ? "<ac:structured-macro ac:name=\"status\">" +
                  "<ac:parameter ac:name=\"title\">Aprobado</ac:parameter>" +
                  "<ac:parameter ac:name=\"color\">Green</ac:parameter>" +
                  "</ac:structured-macro>"
                : "<ac:structured-macro ac:name=\"status\">" +
                  "<ac:parameter ac:name=\"title\">No aprobado</ac:parameter>" +
                  "<ac:parameter ac:name=\"color\">Red</ac:parameter>" +
                  "</ac:structured-macro>";
    
            html.append("<tr style='").append(rowStyle).append("'>")
                .append("<td>").append(icono).append("</td>")
                .append("<td>").append(r.getNombre()).append("</td>")
                .append("<td>").append(String.format("%.2f", r.getPuntaje())).append("</td>")
                .append("<td>").append(macroStatus).append("</td>")
                .append("</tr>");
        }
    
        html.append("</table>");
        return html.toString();
    }
    
}

