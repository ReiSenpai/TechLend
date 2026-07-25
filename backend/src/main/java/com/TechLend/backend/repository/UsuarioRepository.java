package com.TechLend.backend.repository;

import com.TechLend.backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Método clave para que Spring Security encuentre al usuario en el login
    Optional<Usuario> findByCorreoInstitucional(String correoInstitucional);
}