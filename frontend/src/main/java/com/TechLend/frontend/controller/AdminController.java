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
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RestTemplate restTemplate;
    private final String BACKEND_URL = "http://localhost:8080/api";

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Consumimos GET /api/reportes/rotacion[cite: 2]
        ResponseEntity<String> response = restTemplate.getForEntity(
                BACKEND_URL + "/reportes/rotacion", String.class);
        
        model.addAttribute("reporte", response.getBody());
        return "admin/dashboard";
    }

    @GetMapping("/usuarios")
    public String gestionUsuarios() {
        return "admin/usuarios";
    }

    @PostMapping("/usuarios/registro")
    public String registrarPersonal(
            @RequestParam String nombres, 
            @RequestParam String correo, 
            @RequestParam String rol, 
            @RequestParam String password) {
        
        Map<String, String> request = new HashMap<>();
        request.put("nombres", nombres);
        request.put("correoInstitucional", correo);
        request.put("rol", rol);
        request.put("password", password);

        // Envía el usuario interno al backend
        restTemplate.postForEntity(BACKEND_URL + "/auth/registro", request, String.class);
        
        return "redirect:/admin/usuarios?registro=exito";
    }
}