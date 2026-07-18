package com.TechLend.backend.model;
import com.TechLend.backend.model.enums.EstadoEquipo;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "equipos")
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigoPatrimonial;
    private String marcaModelo;

    @Enumerated(EnumType.STRING)
    private EstadoEquipo estadoActual;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaEquipo categoria;
}
