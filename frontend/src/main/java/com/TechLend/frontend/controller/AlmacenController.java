package com.TechLend.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/almacen")
public class AlmacenController {

    @Autowired
    private RestTemplate restTemplate;
    private final String BACKEND_URL = "http://localhost:8080/api/prestamos";

    @GetMapping("/pendientes")
    public String pendientes(Model model) {
        // Aquí deberías crear un endpoint en el backend que retorne solo los pendientes
        // Por ahora simularemos la redirección a la vista
        return "almacen/pendientes";
    }

    @PostMapping("/{id}/aprobar")
    public String aprobarSolicitud(@PathVariable Long id) {
        // Consumimos PUT /api/prestamos/{id}/aprobar[cite: 2]
        restTemplate.put(BACKEND_URL + "/" + id + "/aprobar", null);
        return "redirect:/almacen/pendientes?aprobado=true";
    }

    @GetMapping("/devoluciones")
    public String devoluciones(Model model) {
        return "almacen/devoluciones";
    }

    @PostMapping("/{id}/devolucion")
    public String registrarDevolucion(@PathVariable Long id) {
        // Consumimos PUT /api/prestamos/{id}/devolucion[cite: 2]
        restTemplate.put(BACKEND_URL + "/" + id + "/devolucion", null);
        return "redirect:/almacen/devoluciones?devuelto=true";
    }
}