package com.TechLend.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import com.TechLend.backend.model.Usuario;
import com.TechLend.backend.service.UsuarioService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;

    public AuthRestController(UsuarioService usuarioService, AuthenticationManager authenticationManager) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/registro")
    public ResponseEntity<String> registrarUsuario(@RequestBody Usuario usuario) {
        usuarioService.registrarUsuario(usuario);
        return ResponseEntity.ok("Usuario registrado");
    }

    @PostMapping("/login")
    public ResponseEntity<String> procesarLogin(@RequestBody Map<String, String> request) {
        try {
            String correo = request.get("correoInstitucional");
            String password = request.get("password");

            // 1. Spring Security valida el correo y la contraseña cifrada
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(correo, password)
            );

            // 2. Extraemos el Rol que Spring Security le asignó al usuario
            String rol = authentication.getAuthorities().stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse("SOLICITANTE"); // Valor por defecto de seguridad

            // 3. Devolvemos el ROL (ej: "ROLE_ADMINISTRADOR" o "ADMINISTRADOR") al frontend
            return ResponseEntity.ok(rol);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
    }
}