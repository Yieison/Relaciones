package com.ejemplo.relaciones.controller;

import com.ejemplo.relaciones.dto.CategoriaDTO;
import com.ejemplo.relaciones.service.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<CategoriaDTO> listarCategorias() {
        return categoriaService.listarCategorias();
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> crearCategoria(@RequestBody CategoriaDTO categoriaDto) {
        CategoriaDTO nuevaCategoria = categoriaService.guardarCategoria(categoriaDto);
        return ResponseEntity.ok(nuevaCategoria);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> obtenerCategoria(@PathVariable Long id) {
        try {
            CategoriaDTO categoria = categoriaService.obtenerCategoria(id);
            return ResponseEntity.ok(categoria);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> actualizarCategoria(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDto) {
        try {
            CategoriaDTO categoriaActualizada = categoriaService.actualizarCategoria(id, categoriaDto);
            return ResponseEntity.ok(categoriaActualizada);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        try {
            categoriaService.eliminarCategoria(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
