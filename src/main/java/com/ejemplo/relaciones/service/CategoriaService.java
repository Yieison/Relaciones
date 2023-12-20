package com.ejemplo.relaciones.service;

import com.ejemplo.relaciones.model.Categoria;
import com.ejemplo.relaciones.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    @Transactional
    public Categoria guardarCategoria(Categoria categoria) {
        // Aquí se podría agregar lógica adicional antes de guardar la categoría
        return categoriaRepository.save(categoria);
    }

    public Categoria obtenerCategoria(Long id) {
        // Usamos orElseThrow para lanzar una excepción si la categoría no se encuentra
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No se encontró la Categoría con ID: " + id));
    }

    @Transactional
    public Categoria actualizarCategoria(Long id, Categoria categoria) {
        return categoriaRepository.findById(id)
                .map(categoriaExistente -> {
                    // Aquí puedes copiar las propiedades que deseas actualizar de 'categoria' a 'categoriaExistente'
                    categoriaExistente.setNombre(categoria.getNombre());
                    // ... copiar otras propiedades si son necesarias
                    return categoriaRepository.save(categoriaExistente);
                }).orElseThrow(() -> new NoSuchElementException("No se encontró la Categoría con ID: " + id));
    }

    @Transactional
    public void eliminarCategoria(Long id) {
        try {
            categoriaRepository.deleteById(id);
        } catch (Exception e) {
            // Aquí se puede manejar de manera más específica la excepción, por ejemplo, si la categoría no se puede eliminar debido a restricciones de clave foránea, etc.
            throw new IllegalArgumentException("No se pudo eliminar la Categoría con ID: " + id, e);
        }
    }
}
