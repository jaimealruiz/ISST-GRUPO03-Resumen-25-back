package com.isst.resumenes.controllers;

import com.isst.resumenes.models.Resumen;
import com.isst.resumenes.services.ResumenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/resumenes")
public class ResumenController {

    private final ResumenService resumenService;

    // Constructor que recibe el servicio de resúmenes
    public ResumenController(ResumenService resumenService) {
        this.resumenService = resumenService;
    }

    // Método para subir un resumen con un archivo, solo cuando se este registrado
    @PostMapping("/crear")
    public ResponseEntity<String> subirResumen(
            @RequestParam("titulo") String titulo,
            @RequestParam("email") String email,
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam("ejemplo") boolean ejemplo) {

        try {
            resumenService.guardarResumen(titulo, email, archivo, ejemplo);
            return ResponseEntity.status(HttpStatus.CREATED).body("Resumen guardado correctamente");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el archivo");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Método para listar todos los resúmenes, solo cuando se este registrado
    @GetMapping("/listar")
    public ResponseEntity<List<Resumen>> listarResúmenes(@RequestParam(value = "validados", required = false, defaultValue = "true") boolean validados) {
        return ResponseEntity.ok(resumenService.listarResúmenes(validados));
    }

    // Método para obtener resúmenes de un autor por email, solo cuando se este registrado
    @GetMapping("/autor/{email}")
    public ResponseEntity<?> listarResúmenesPorAutor(@PathVariable String email) {
        try {
            return ResponseEntity.ok(resumenService.listarResúmenesPorAutor(email));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron resúmenes para este autor");
        }
    }

    // Método para listar solo los resúmenes de ejemplo, sin necesidad de nada
    @GetMapping("/ejemplo")
    public ResponseEntity<List<Resumen>> listarResumenesEjemplo() {
        return ResponseEntity.ok(resumenService.listarResumenesEjemplo());
    }

    // Método para validar un resumen, solo si es admin
    @PostMapping("/validar/{id}")
    public ResponseEntity<String> validarResumen(@PathVariable Long id) {
        try {
            resumenService.validarResumen(id);
            return ResponseEntity.ok("Resumen validado exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el resumen");
        }
    }
}
