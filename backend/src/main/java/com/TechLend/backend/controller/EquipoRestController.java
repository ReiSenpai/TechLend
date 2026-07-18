package com.TechLend.backend.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.TechLend.backend.model.enums.EstadoEquipo;
import com.TechLend.backend.repository.EquipoRepository;



@RestController
@RequestMapping("/api/equipos")
public class EquipoRestController {

    private final EquipoRepository equipoRepository;

    public EquipoRestController(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }

    @GetMapping // GET /api/equipos?estado=disponible[cite: 2]
    public ResponseEntity<?> listarEquipos(@RequestParam(required = false) String estado) {
        if ("disponible".equalsIgnoreCase(estado)) {
            return ResponseEntity.ok(equipoRepository.findByEstadoActual(EstadoEquipo.DISPONIBLE));
        }
        return ResponseEntity.ok(equipoRepository.findAll());
    }
}
