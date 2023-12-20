package com.ejemplo.relaciones.controller;

import com.ejemplo.relaciones.dto.LibroDTO;
import com.ejemplo.relaciones.service.LibroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/libros")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    public List<LibroDTO> listarLibros() {
        return libroService.listarLibros();
    }

    @PostMapping
    public ResponseEntity<LibroDTO> crearLibro(@RequestBody LibroDTO libroDto) {
        LibroDTO nuevoLibro = libroService.guardarLibro(libroDto);
        return ResponseEntity.ok(nuevoLibro);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibroDTO> obtenerLibro(@PathVariable Long id) {
        try {
            LibroDTO libroDto = libroService.obtenerLibro(id);
            return ResponseEntity.ok(libroDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibroDTO> actualizarLibro(@PathVariable Long id, @RequestBody LibroDTO libroDto) {
        try {
            LibroDTO libroActualizado = libroService.actualizarLibro(id, libroDto);
            return ResponseEntity.ok(libroActualizado);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLibro(@PathVariable Long id) {
        try {
            libroService.eliminarLibro(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
