package com.TechLend.backend.model;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "sancion")
public class Sancion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "codigo_sancion")
    private String codigoSancion;
    
    private String motivo;
    private Double monto;
    
    @Column(name = "dias_suspension")
    private Integer diasSuspension;
    
    private String estado;
    
    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;
    
    @ManyToOne
    @JoinColumn(name = "prestamo_id")
    private Prestamo prestamo;
}