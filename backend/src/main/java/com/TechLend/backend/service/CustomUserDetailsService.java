// src/main/java/com/TechLend/backend/service/CustomUserDetailsService.java
package com.TechLend.backend.service;

import com.TechLend.backend.model.Usuario;
import com.TechLend.backend.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository repository;

    public CustomUserDetailsService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = repository.findByCorreoInstitucional(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
            
        return new org.springframework.security.core.userdetails.User(
            usuario.getCorreoInstitucional(),
            usuario.getContrasena(),
            Collections.singletonList(new SimpleGrantedAuthority(usuario.getRol().name()))
        );
    }
}