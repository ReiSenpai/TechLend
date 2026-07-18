package com.TechLend.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TechLend.backend.model.Equipo;
import com.TechLend.backend.model.enums.EstadoEquipo;

import java.util.List;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    List<Equipo> findByEstadoActual(EstadoEquipo estado);
}