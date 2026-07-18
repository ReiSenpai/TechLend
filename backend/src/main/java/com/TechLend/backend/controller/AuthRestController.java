package com.TechLend.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.TechLend.backend.model.Usuario;
import com.TechLend.backend.service.UsuarioService;


@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private final UsuarioService usuarioService;

    public AuthRestController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registro")
    public ResponseEntity<String> registrarUsuario(@RequestBody Usuario usuario) {
        usuarioService.registrarUsuario(usuario);
        return ResponseEntity.ok("Usuario registrado");
    }
}
