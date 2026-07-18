package com.TechLend.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {

    @Autowired
    private RestTemplate restTemplate;
    
    private final String BACKEND_URL = "http://localhost:8080/api/auth";

    @GetMapping("/login")
    public String mostrarLogin() {
        return "auth/login";
    }

    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "auth/registro";
    }

    @PostMapping("/registro")
    public String procesarRegistro(
            @RequestParam String nombres, 
            @RequestParam String correoInstitucional, 
            @RequestParam String password) {
        
        // Armamos el JSON para enviar al backend
        Map<String, String> request = new HashMap<>();
        request.put("nombres", nombres);
        request.put("correoInstitucional", correoInstitucional);
        request.put("password", password);
        // El backend asignará automáticamente el rol SOLICITANTE

        restTemplate.postForEntity(BACKEND_URL + "/registro", request, String.class);
        
        return "redirect:/login?exito";
    }
}