package com.TechLend.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "categoria_equipo")
public class CategoriaEquipo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    
    @Column(name = "condiciones_uso")
    private String condicionesUso;
}