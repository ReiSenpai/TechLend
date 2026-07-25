package com.TechLend.backend.controller;

import com.TechLend.backend.model.Sancion;
import com.TechLend.backend.repository.SancionRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sanciones")
@CrossOrigin(origins = "*")
public class SancionRestController {

    private final SancionRepository sancionRepo;

    public SancionRestController(SancionRepository sancionRepo) {
        this.sancionRepo = sancionRepo;
    }

    // Endpoint consumido por la tabla de admin-sanciones.html
    @GetMapping
    public List<Sancion> getAllSanciones() {
        return sancionRepo.findAll();
    }
}