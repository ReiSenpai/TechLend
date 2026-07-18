package com.TechLend.backend.model;

import com.TechLend.backend.model.enums.RolUsuario;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombres;

    @Column(unique = true, nullable = false)
    private String correoInstitucional;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private RolUsuario rol;
    private boolean activo = true;
}
