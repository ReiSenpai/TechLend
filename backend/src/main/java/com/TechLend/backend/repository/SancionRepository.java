package com.TechLend.backend.repository;

import com.TechLend.backend.model.Sancion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SancionRepository extends JpaRepository<Sancion, Long> {
    
    // Filtro para saber cuántas sanciones "Activas" hay para los KPIs OLAP
    List<Sancion> findByEstado(String estado);
}