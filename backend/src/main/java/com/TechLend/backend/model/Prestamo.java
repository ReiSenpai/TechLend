package com.TechLend.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "prestamo")
public class Prestamo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "codigo_solicitud")
    private String codigoSolicitud;
    
    @ManyToOne
    @JoinColumn(name = "equipo_id")
    private Equipo equipo;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    @Column(name = "fecha_solicitud")
    private LocalDateTime fechaSolicitud;
    
    // Agregamos la fecha de entrega
    @Column(name = "fecha_entrega")
    private LocalDateTime fechaEntrega;
    
    @Column(name = "fecha_devolucion_prevista")
    private LocalDateTime fechaDevolucionPrevista;
    
    // Agregamos la fecha de devolución real que faltaba
    @Column(name = "fecha_devolucion_real")
    private LocalDateTime fechaDevolucionReal;
    
    private String motivo;
    
    private String estado; 
}