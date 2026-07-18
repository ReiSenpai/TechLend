package com.TechLend.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.TechLend.backend.repository.PrestamoRepository;

@RestController
@RequestMapping("/api")
public class ReporteRestController {

    private final PrestamoRepository prestamoRepository;

    public ReporteRestController(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    @GetMapping("/reportes/rotacion") // GET /api/reportes/rotacion[cite: 2]
    public ResponseEntity<?> generarReporteRotacion() {
        return ResponseEntity.ok("Reporte de rotación generado");
    }
    
    @GetMapping("/usuarios/{id}/historial") // GET /api/usuarios/{id}/historial[cite: 2]
    public ResponseEntity<?> consultarHistorial(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoRepository.findBySolicitanteId(id));
    }
}
