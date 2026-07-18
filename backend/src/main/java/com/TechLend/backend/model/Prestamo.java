package com.TechLend.backend.model;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

import com.TechLend.backend.model.enums.EstadoPrestamo;

@Data
@Entity
@Table(name = "prestamos")
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaEntrega;
    private LocalDateTime fechaDevolucionPrevista;

    @Enumerated(EnumType.STRING)
    private EstadoPrestamo estado;

    @ManyToOne
    @JoinColumn(name = "solicitante_id")
    private Usuario solicitante;

    @ManyToOne
    @JoinColumn(name = "equipo_id")
    private Equipo equipo;
}
