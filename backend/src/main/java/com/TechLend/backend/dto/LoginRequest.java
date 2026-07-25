package com.TechLend.backend.dto;
import lombok.Data;

@Data
public class LoginRequest {
    private String correo;
    private String contrasena;

    // Getters y Setters obligatorios para que Spring pueda leer el JSON
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}