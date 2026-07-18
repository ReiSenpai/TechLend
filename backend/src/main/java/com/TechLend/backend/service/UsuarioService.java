package com.TechLend.backend.service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.TechLend.backend.model.Usuario;
import com.TechLend.backend.model.enums.RolUsuario;
import com.TechLend.backend.repository.UsuarioRepository;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registrarUsuario(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); // Cifrado RNF-03[cite: 2]
        usuario.setRol(RolUsuario.SOLICITANTE);
        usuario.setActivo(true);
        return usuarioRepository.save(usuario);
    }
}
