package com.ejemplo.relaciones.service;

import com.ejemplo.relaciones.model.Libro;
import com.ejemplo.relaciones.repository.LibroRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    @Transactional
    public Libro guardarLibro(Libro libro) {
        // Aquí puedes agregar validaciones o lógica antes de guardar el libro
        return libroRepository.save(libro);
    }

    public Optional<Libro> obtenerLibro(Long id) {
        return libroRepository.findById(id);
    }

    @Transactional
    public Libro actualizarLibro(Long id, Libro libro) {
        return libroRepository.findById(id)
                .map(libroExistente -> {
                    // Aquí puedes copiar las propiedades que desees actualizar desde 'libro' a 'libroExistente'
                    libroExistente.setTitulo(libro.getTitulo());
                    // ... copiar otras propiedades
                    return libroRepository.save(libroExistente);
                }).orElseThrow(() -> new IllegalArgumentException("Libro no encontrado con ID: " + id));
    }

    @Transactional
    public void eliminarLibro(Long id) {
        try {
            libroRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("Libro no encontrado con ID: " + id, e);
        }
    }
}
