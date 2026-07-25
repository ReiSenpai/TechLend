package com.TechLend.backend.controller;

import com.TechLend.backend.dto.LoginRequest;
import com.TechLend.backend.model.Usuario;
import com.TechLend.backend.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID; // Temporal para simular JWT si aún no configuras JwtService

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private final AuthenticationManager authManager;
    private final UsuarioRepository usuarioRepo;

    public AuthRestController(AuthenticationManager authManager, UsuarioRepository usuarioRepo) {
        this.authManager = authManager;
        this.usuarioRepo = usuarioRepo;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Spring Security valida automáticamente contra la base de datos y la contraseña BCrypt
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getContrasena())
        );

        Usuario usuario = usuarioRepo.findByCorreoInstitucional(request.getCorreo()).orElseThrow();

        // AQUÍ generas el JWT (En este snippet uso un string temporal para que compile)
        String token = "jwt_generado_" + UUID.randomUUID().toString(); 

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("rol", usuario.getRol().name());
        response.put("nombres", usuario.getNombres());

        return ResponseEntity.ok(response);
    }
}