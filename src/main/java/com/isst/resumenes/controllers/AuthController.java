package com.isst.resumenes.controllers;

import com.isst.resumenes.models.Usuario;
import com.isst.resumenes.services.UsuarioService;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    // Constructor que recibe el servicio de usuario
    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Método para registrar un usuario
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Usuario usuario) {
        usuarioService.registrarUsuario(usuario);
        return ResponseEntity.ok("Usuario registrado exitosamente");
    }

    // Método de login (simulado, no hay autenticación real aun)
    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody Usuario usuario) {
        Optional<Usuario> usuarioByEmail = usuarioService.buscarPorEmail(usuario.getEmail());
        if(usuarioByEmail.isPresent()){
            if(usuarioByEmail.get().getPassword().equals(usuario.getPassword())){
                return ResponseEntity.ok(true); // Solo devuelve true si llega aquí
            } 
            
        }
        return ResponseEntity.badRequest().build();
        
    }
}
