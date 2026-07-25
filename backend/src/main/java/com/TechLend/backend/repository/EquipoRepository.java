package com.TechLend.backend.repository;

import com.TechLend.backend.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    // SOLUCIÓN AL ERROR: Le decimos exactamente qué campos buscar
    @Query("SELECT e FROM Equipo e WHERE e.estadoActual = :estado AND e.categoria.nombre = :categoria")
    List<Equipo> findByEstadoAndCategoria(@Param("estado") String estado, @Param("categoria") String categoria);

    // Los métodos que agregamos anteriormente para las métricas
    long countByEstadoActual(String estadoActual);

    @Query("SELECT (COUNT(e) * 100.0 / (SELECT COUNT(eq) FROM Equipo eq)) FROM Equipo e WHERE e.estadoActual = 'Disponible'")
    Double calcularPorcentajeDisponibilidad();

    // Agrega esto dentro de EquipoRepository
    @Query("SELECT e FROM Equipo e WHERE e.estadoActual = :estado")
    List<Equipo> findByEstado(@Param("estado") String estado);
}