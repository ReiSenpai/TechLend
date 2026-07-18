package com.TechLend.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notificaciones")
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String tipo;
    private LocalDateTime fechaEnvio;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario destinatario;
}
