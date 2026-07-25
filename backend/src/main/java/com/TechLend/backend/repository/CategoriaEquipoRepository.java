package com.TechLend.backend.repository;

import com.TechLend.backend.model.CategoriaEquipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaEquipoRepository extends JpaRepository<CategoriaEquipo, Long> {
}