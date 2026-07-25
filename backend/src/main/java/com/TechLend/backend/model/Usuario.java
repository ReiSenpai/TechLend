// src/main/java/com/TechLend/backend/model/Usuario.java
package com.TechLend.backend.model;

import com.TechLend.backend.model.enums.RolUsuario;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombres;
    
    @Enumerated(EnumType.STRING)
    private RolUsuario rol;
    
    @Column(name = "correo_institucional", unique = true)
    private String correoInstitucional;
    
    private String contrasena;
    private String estado = "Activo";
}