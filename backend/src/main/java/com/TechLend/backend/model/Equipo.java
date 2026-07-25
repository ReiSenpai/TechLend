package com.TechLend.backend.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "equipo")
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "codigo_patrimonial")
    private String codigoPatrimonial;
    
    private String tipo;
    
    @Column(name = "marca_modelo")
    private String marcaModelo;
    
    @Column(name = "estado_actual")
    private String estadoActual;
    
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaEquipo categoria;
    
    // Campo temporal si deseas manejar imágenes dinámicas
    @Transient 
    private String imagen; 
}