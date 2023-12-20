package com.ejemplo.relaciones.service;

import com.ejemplo.relaciones.model.Autor;
import com.ejemplo.relaciones.repository.AutorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<Autor> listarAutores() {
        return autorRepository.findAll();
    }

    @Transactional
    public Autor guardarAutor(Autor autor) {
        // Verificar si el autor ya existe (basado en el nombre)
        Optional<Autor> autorExistente = autorRepository.findByNombre(autor.getNombre());
        if (autorExistente.isPresent()) {
            throw new IllegalStateException("Ya existe un autor con el nombre " + autor.getNombre());
        }
        return autorRepository.save(autor);
    }

    public Optional<Autor> obtenerAutor(Long id) {
        return autorRepository.findById(id);
    }

    @Transactional
    public Autor actualizarAutor(Long id, Autor autor) {
        return autorRepository.findById(id)
                .map(autorExistente -> {
                    autorExistente.setNombre(autor.getNombre());
                    // Aquí puedes agregar la lógica para actualizar otros campos si es necesario
                    return autorRepository.save(autorExistente);
                }).orElseThrow(() -> new IllegalStateException("No se encontró el Autor con ID: " + id));
    }

    @Transactional
    public void eliminarAutor(Long id) {
        if (!autorRepository.existsById(id)) {
            throw new IllegalStateException("No se encontró el Autor con ID: " + id);
        }
        autorRepository.deleteById(id);
    }
}
