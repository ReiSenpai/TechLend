package com.TechLend.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.TechLend.backend.model.Sancion;

@Repository
public interface SancionRepository extends JpaRepository<Sancion, Long> {
}
