package com.TechLend.backend.controller;

import com.TechLend.backend.model.Prestamo;
import com.TechLend.backend.model.Equipo;
import com.TechLend.backend.repository.PrestamoRepository;
import com.TechLend.backend.repository.EquipoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
@CrossOrigin("*")
public class PrestamoRestController {

    private final PrestamoRepository prestamoRepo;
    private final EquipoRepository equipoRepo;

    public PrestamoRestController(PrestamoRepository prestamoRepo, EquipoRepository equipoRepo) {
        this.prestamoRepo = prestamoRepo;
        this.equipoRepo = equipoRepo;
    }

    // Para el Dashboard del Encargado (Carga todas las solicitudes)
    @GetMapping("/activos")
    public List<Prestamo> getActivos() {
        return prestamoRepo.findAll(); 
    }

    // Acciones del Encargado (Checkout / Checkin)
    // CAMBIO: Se ajustó el ID a Long
    @PutMapping("/{id}/{accion}")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @PathVariable String accion) {
        Prestamo prestamo = prestamoRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));
            
        Equipo equipo = prestamo.getEquipo();

        switch (accion.toLowerCase()) {
            case "aprobar":
                prestamo.setEstado("Aprobado");
                break;
            case "rechazar":
                prestamo.setEstado("Rechazado");
                break;
            case "entrega": // Checkout
                prestamo.setEstado("Entregado");
                // CAMBIO: Se usa setEstadoActual() basado en tu modelo Equipo.java
                equipo.setEstadoActual("Prestado");
                equipoRepo.save(equipo);
                break;
            case "devolucion": // Checkin
                prestamo.setEstado("Devuelto");
                prestamo.setFechaDevolucionReal(LocalDateTime.now());
                equipo.setEstadoActual("Disponible");
                equipoRepo.save(equipo);
                break;
            default:
                return ResponseEntity.badRequest().body("Acción no válida");
        }
        
        prestamoRepo.save(prestamo);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/equipo/{equipoId}")
    public List<Prestamo> getHistorialPorEquipo(@PathVariable Long equipoId) {
        return prestamoRepo.findByEquipoId(equipoId);
    }
}