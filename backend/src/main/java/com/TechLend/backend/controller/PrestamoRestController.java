package com.TechLend.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.TechLend.backend.model.Prestamo;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoRestController {

    @PostMapping // POST /api/prestamos[cite: 2]
    public ResponseEntity<?> registrarSolicitud(@RequestBody Prestamo prestamo) {
        return ResponseEntity.ok("Solicitud registrada");
    }

    @PutMapping("/{id}/aprobar") // PUT /api/prestamos/{id}/aprobar[cite: 2]
    public ResponseEntity<?> aprobarSolicitud(@PathVariable Long id) {
        return ResponseEntity.ok("Préstamo " + id + " aprobado");
    }

    @PutMapping("/{id}/devolucion") // PUT /api/prestamos/{id}/devolucion[cite: 2]
    public ResponseEntity<?> registrarDevolucion(@PathVariable Long id) {
        return ResponseEntity.ok("Devolución registrada");
    }
}
