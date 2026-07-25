package com.TechLend.backend.controller;

import com.TechLend.backend.model.Equipo;
import com.TechLend.backend.repository.EquipoRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/equipos")
@CrossOrigin("*")
public class EquipoRestController {

    private final EquipoRepository repository;

    public EquipoRestController(EquipoRepository repository) {
        this.repository = repository;
    }

    // Endpoint para Administrador (Todos los equipos)
    @GetMapping
    public List<Equipo> getAll() {
        return repository.findAll();
    }

    // Endpoint para Solicitante (Filtrado para el catálogo)
    @GetMapping("/disponibles")
    public List<Equipo> getDisponibles(@RequestParam(required = false) String categoria) {
        if (categoria != null && !categoria.equals("Todos")) {
            // Requiere método en Repository: findByEstadoAndCategoria
            return repository.findByEstadoAndCategoria("Disponible", categoria);
        }
        // Requiere método en Repository: findByEstado
        return repository.findByEstado("Disponible");
    }
    // NUEVO: Endpoint para registrar un equipo desde el formulario
    @PostMapping
    public Equipo crearEquipo(@RequestBody Equipo equipo) {
        // Por defecto, todo equipo nuevo entra como "Disponible"
        if(equipo.getEstadoActual() == null) {
            equipo.setEstadoActual("Disponible");
        }
        return repository.save(equipo);
    }
    @PutMapping("/{id}")
    public Equipo actualizarEquipo(@PathVariable Long id, @RequestBody Equipo equipoActualizado) {
        Equipo equipoExistente = repository.findById(id).orElseThrow();
        equipoExistente.setCodigoPatrimonial(equipoActualizado.getCodigoPatrimonial());
        equipoExistente.setTipo(equipoActualizado.getTipo());
        equipoExistente.setMarcaModelo(equipoActualizado.getMarcaModelo());
        equipoExistente.setCategoria(equipoActualizado.getCategoria());
        equipoExistente.setEstadoActual(equipoActualizado.getEstadoActual()); // Permite cambiar estado
        return repository.save(equipoExistente);
    }
}