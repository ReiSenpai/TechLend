package com.TechLend.backend.config;

import com.TechLend.backend.model.Usuario;
import com.TechLend.backend.model.enums.RolUsuario; // Importación del Enum agregada
import com.TechLend.backend.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            
            // 1. Cuenta de Administrador
            if (usuarioRepository.findByCorreoInstitucional("admin@utp.edu.pe").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setNombres("Ing. Pedro Flores");
                admin.setCorreoInstitucional("admin@utp.edu.pe");
                admin.setContrasena(passwordEncoder.encode("123456"));
                // CORRECCIÓN: Uso del Enum en lugar de String
                admin.setRol(RolUsuario.Administrador); 
                admin.setEstado("Activo");
                usuarioRepository.save(admin);
                System.out.println("✅ Cuenta Administrador creada.");
            }

            // 2. Cuenta de Encargado de Almacén
            if (usuarioRepository.findByCorreoInstitucional("almacen@utp.edu.pe").isEmpty()) {
                Usuario encargado = new Usuario();
                encargado.setNombres("Tec. Rosa Mendoza");
                encargado.setCorreoInstitucional("almacen@utp.edu.pe");
                encargado.setContrasena(passwordEncoder.encode("123456"));
                // CORRECCIÓN: Uso del Enum en lugar de String
                encargado.setRol(RolUsuario.Encargado);
                encargado.setEstado("Activo");
                usuarioRepository.save(encargado);
                System.out.println("✅ Cuenta Encargado creada.");
            }

            // 3. Cuenta de Solicitante
            if (usuarioRepository.findByCorreoInstitucional("c.quispe@utp.edu.pe").isEmpty()) {
                Usuario solicitante = new Usuario();
                solicitante.setNombres("Dr. Carlos Quispe");
                solicitante.setCorreoInstitucional("c.quispe@utp.edu.pe");
                solicitante.setContrasena(passwordEncoder.encode("123456"));
                // CORRECCIÓN: Uso del Enum en lugar de String
                solicitante.setRol(RolUsuario.Solicitante);
                solicitante.setEstado("Activo");
                usuarioRepository.save(solicitante);
                System.out.println("✅ Cuenta Solicitante creada.");
            }
        };
    }
}