package com.kata.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class ConfluenceService {

    @Value("${confluence.api.base-url}") // Url https://oscar-torres.atlassian.net/wiki
    private String baseUrl;

    @Value("${confluence.api.email}") // Correo
    private String email;

    @Value("${confluence.api.token}") // Tu API token
    private String apiToken;

    @Value("${confluence.api.space-key}") // KATA
    private String spaceKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public void publicarEnConfluence(String titulo, String contenidoHtml) {
        String url = baseUrl + "/rest/api/content";

        // Codificar credenciales en base64 para el header Authorization
        String credentials = email + ":" + apiToken;
        String encodedAuth = Base64.getEncoder()
                                   .encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic " + encodedAuth);

        // Construir el cuerpo del POST
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("type", "page");
        requestBody.put("title", titulo);

        Map<String, String> space = new HashMap<>();
        space.put("key", spaceKey);
        requestBody.put("space", space);

        Map<String, String> storage = new HashMap<>();
        storage.put("value", contenidoHtml);
        storage.put("representation", "storage");

        Map<String, Object> bodyContent = new HashMap<>();
        bodyContent.put("storage", storage);
        requestBody.put("body", bodyContent);

        // Ejecutar el POST a la API de Confluence
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("✅ Página publicada exitosamente.");
        } else {
            System.out.println("❌ Error al publicar: " + response.getStatusCode());
            System.out.println(response.getBody());
        }
    }
}

