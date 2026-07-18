package com.TechLend.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

    // ---------------------------------------------------------
    // RUTAS PARA EL LOGIN
    // ---------------------------------------------------------
    
    @GetMapping("/login")
    public String mostrarLogin() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String procesarLogin(
            @RequestParam String correoInstitucional, 
            @RequestParam String password) {
        
        try {
            Map<String, String> request = new HashMap<>();
            request.put("correoInstitucional", correoInstitucional);
            request.put("password", password);

            // Consumimos el endpoint y CAPTURAMOS la respuesta del backend
            ResponseEntity<String> response = restTemplate.postForEntity(BACKEND_URL + "/login", request, String.class);
            
            // El backend ahora nos debe devolver el ROL en formato texto
            String rol = response.getBody(); 

            // Redirección dinámica basada en el rol del usuario
            if (rol != null) {
                if (rol.contains("ADMINISTRADOR")) {
                    return "redirect:/admin/dashboard";
                } else if (rol.contains("ENCARGADO")) {
                    return "redirect:/almacen/pendientes";
                }
            }
            
            // Por defecto, redirigimos al portal del solicitante
            return "redirect:/solicitante/dashboard";
            
        } catch (Exception e) {
            return "redirect:/login?error=true";
        }
    }

    // ---------------------------------------------------------
    // RUTAS PARA EL REGISTRO
    // ---------------------------------------------------------

    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "auth/registro";
    }

    @PostMapping("/registro")
    public String procesarRegistro(
            @RequestParam String nombres, 
            @RequestParam String correoInstitucional, 
            @RequestParam String password) {
        
        try {
            Map<String, String> request = new HashMap<>();
            request.put("nombres", nombres);
            request.put("correoInstitucional", correoInstitucional);
            request.put("password", password);

            restTemplate.postForEntity(BACKEND_URL + "/registro", request, String.class);
            return "redirect:/login?exito";
            
        } catch (Exception e) {
            return "redirect:/registro?error=true";
        }
    }
}