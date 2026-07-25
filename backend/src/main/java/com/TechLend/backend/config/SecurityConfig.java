package com.TechLend.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
// 1. IMPORTANTE: Agregar esta importación
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 2. IMPORTANTE: Inyectar el filtro JWT que creaste
    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(request -> {
                CorsConfiguration conf = new CorsConfiguration();
                conf.setAllowedOrigins(List.of("*"));
                conf.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                conf.setAllowedHeaders(List.of("*"));
                return conf;
            }))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll() // Login público
                .requestMatchers("/api/equipos/**").hasAnyAuthority("Solicitante", "Encargado", "Administrador")
                .requestMatchers("/api/prestamos/aprobar", "/api/prestamos/devolucion", "/api/prestamos/entrega").hasAuthority("Encargado")
                .requestMatchers("/api/admin/**", "/api/reportes/**", "/api/sanciones/**").hasAuthority("Administrador")
                .anyRequest().authenticated()
            ) // 3. IMPORTANTE: Se eliminó el punto y coma (;) que estaba aquí
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}