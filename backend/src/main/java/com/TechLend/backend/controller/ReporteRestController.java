package com.TechLend.backend.controller;

import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin("*")
public class ReporteRestController {

    // Simula los KPIs OLAP basándose en tu diseño React
    @GetMapping("/dashboard")
    public Map<String, Object> getOlapMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("rotacionPromedio", "4.2×");
        metrics.put("disponibilidadActual", "84%");
        metrics.put("tasaRetraso", "12%");
        metrics.put("duracionPromedio", "3.8d");
        
        // Aquí podrías agregar las listas `DEMAND_DATA` y `MONTHLY_DATA` de tu React
        // metrics.put("demandaCategorias", ...);
        
        return metrics;
    }
}