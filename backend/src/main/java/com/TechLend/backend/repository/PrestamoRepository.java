package com.TechLend.backend.repository;

import com.TechLend.backend.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    
    // SOLUCIÓN: Buscar a través de la relación 'usuario' (por ejemplo, por su nombre)
    @Query("SELECT p FROM Prestamo p WHERE p.usuario.nombres = :solicitante")
    List<Prestamo> findBySolicitante(@Param("solicitante") String solicitante);
    
    // O si prefieres buscar por el correo institucional del usuario (recomendado para mayor precisión):
    @Query("SELECT p FROM Prestamo p WHERE p.usuario.correoInstitucional = :correo")
    List<Prestamo> findByUsuarioCorreo(@Param("correo") String correo);
    List<Prestamo> findByEquipoId(Long equipoId);
}