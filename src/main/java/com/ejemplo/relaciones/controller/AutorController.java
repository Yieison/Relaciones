package com.ejemplo.relaciones.controller;

import com.ejemplo.relaciones.dto.AutorDTO;
import com.ejemplo.relaciones.service.AutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping
    public List<AutorDTO> listarAutores() {
        return autorService.listarAutores();
    }

    @PostMapping
    public ResponseEntity<AutorDTO> crearAutor(@RequestBody AutorDTO autorDto) {
        try {
            AutorDTO nuevoAutor = autorService.guardarAutor(autorDto);
            return ResponseEntity.ok(nuevoAutor);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> obtenerAutor(@PathVariable Long id) {
        try {
            AutorDTO autorDto = autorService.obtenerAutor(id);
            return ResponseEntity.ok(autorDto);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }



    @PutMapping("/{id}")
    public ResponseEntity<AutorDTO> actualizarAutor(@PathVariable Long id, @RequestBody AutorDTO autorDto) {
        try {
            AutorDTO autorActualizado = autorService.actualizarAutor(id, autorDto);
            return ResponseEntity.ok(autorActualizado);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAutor(@PathVariable Long id) {
        try {
            autorService.eliminarAutor(id);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
