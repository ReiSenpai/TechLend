package com.TechLend.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.TechLend.backend.model.Usuario;
import com.TechLend.backend.model.enums.RolUsuario;
import com.TechLend.backend.repository.UsuarioRepository;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    // Este Bean se ejecuta automáticamente al iniciar el Backend
    @Bean
    public CommandLineRunner initAdmin(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Verificamos si el correo ya existe para no duplicarlo cada vez que iniciamos
            if (usuarioRepository.findByCorreoInstitucional("admin@utp.edu.pe").isEmpty()) {
                
                Usuario admin = new Usuario();
                admin.setNombres("Administrador del Sistema");
                admin.setCorreoInstitucional("admin@utp.edu.pe");
                
                // Aquí el PasswordEncoder hace el trabajo sucio por nosotros
                admin.setPassword(passwordEncoder.encode("admin123")); 
                
                admin.setRol(RolUsuario.ADMINISTRADOR); //[cite: 1]
                admin.setActivo(true);
                
                usuarioRepository.save(admin);
                System.out.println("✅ Cuenta de Administrador generada exitosamente en la BD.");
            }
        };
    }
}