package com.TechLend.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
        try {
            ResponseEntity<Object[]> response = restTemplate.getForEntity(
                    BACKEND_URL + "/equipos?estado=disponible", Object[].class);
            model.addAttribute("equipos", response.getBody());
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo conectar con el servidor de inventario.");
        }
        return "solicitante/dashboard";
    }

    @PostMapping("/prestamos")
    public String registrarPrestamo(
            @RequestParam Long equipoId, 
            @RequestParam("fechaSolicitud") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaSolicitud) {
        
        try {
            Map<String, Object> request = new HashMap<>();
            request.put("equipoId", equipoId);
            request.put("fechaSolicitud", fechaSolicitud.toString());
            request.put("solicitanteId", 1L); // ID estático temporal

            restTemplate.postForEntity(BACKEND_URL + "/prestamos", request, String.class);
            return "redirect:/solicitante/dashboard?solicitud=enviada";
        } catch (Exception e) {
            return "redirect:/solicitante/dashboard?error=true";
        }
    }
    
    @GetMapping("/historial")
    public String historial(Model model) {
        try {
            ResponseEntity<Object[]> response = restTemplate.getForEntity(
                    BACKEND_URL + "/usuarios/1/historial", Object[].class);
            model.addAttribute("historial", response.getBody());
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar el historial.");
        }
        return "solicitante/historial";
    }
}