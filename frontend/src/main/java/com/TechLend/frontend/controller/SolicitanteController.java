package com.TechLend.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/solicitante")
public class SolicitanteController {

    @Autowired
    private RestTemplate restTemplate;
    private final String BACKEND_URL = "http://localhost:8080/api";

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Consumimos el endpoint GET /api/equipos?estado=disponible
        ResponseEntity<Object[]> response = restTemplate.getForEntity(
                BACKEND_URL + "/equipos?estado=disponible", Object[].class);
        
        model.addAttribute("equipos", response.getBody());
        return "solicitante/dashboard";
    }

    @PostMapping("/prestamos")
    public String registrarPrestamo(@RequestParam Long equipoId, @RequestParam String fechaSolicitud) {
        // Consumimos el endpoint POST /api/prestamos
        Map<String, Object> request = new HashMap<>();
        request.put("equipoId", equipoId);
        request.put("fechaSolicitud", LocalDateTime.parse(fechaSolicitud));
        // Aquí idealmente enviarías el ID del usuario logueado en sesión
        request.put("solicitanteId", 1L); 

        restTemplate.postForEntity(BACKEND_URL + "/prestamos", request, String.class);
        
        return "redirect:/solicitante/dashboard?solicitud=enviada";
    }
    
    @GetMapping("/historial")
    public String historial(Model model) {
        // Asumiendo que el ID del usuario en sesión es 1
        ResponseEntity<Object[]> response = restTemplate.getForEntity(
                BACKEND_URL + "/usuarios/1/historial", Object[].class); //[cite: 2]
        model.addAttribute("historial", response.getBody());
        return "solicitante/historial";
    }
}