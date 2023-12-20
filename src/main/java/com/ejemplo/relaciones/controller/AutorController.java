package com.ejemplo.relaciones.controller;

import com.ejemplo.relaciones.model.Autor;
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
    public List<Autor> listarAutores() {
        return autorService.listarAutores();
    }

    @PostMapping
    public ResponseEntity<Autor> crearAutor(@RequestBody Autor autor) {
        try {
            Autor nuevoAutor = autorService.guardarAutor(autor);
            return ResponseEntity.ok(nuevoAutor);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Autor> obtenerAutor(@PathVariable Long id) {
        return autorService.obtenerAutor(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Autor> actualizarAutor(@PathVariable Long id, @RequestBody Autor autor) {
        try {
            Autor autorActualizado = autorService.actualizarAutor(id, autor);
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
