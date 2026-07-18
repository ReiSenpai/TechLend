package com.TechLend.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "sanciones")
public class Sancion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String motivo;
    private Integer montoODiasSuspension;
    private String estado;

    // Relación: Un préstamo puede derivar en una sanción[cite: 4]
    @OneToOne
    @JoinColumn(name = "prestamo_id")
    private Prestamo prestamo;
}