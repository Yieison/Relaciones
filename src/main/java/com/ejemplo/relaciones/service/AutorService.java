package com.ejemplo.relaciones.service;

import com.ejemplo.relaciones.model.Autor;
import com.ejemplo.relaciones.repository.AutorRepository;
import com.ejemplo.relaciones.dto.AutorDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<AutorDTO> listarAutores() {
        return autorRepository.findAll().stream()
                              .map(this::convertirADto)
                              .collect(Collectors.toList());
    }

    @Transactional
    public AutorDTO guardarAutor(AutorDTO autorDto) {
        Optional<Autor> autorExistente = autorRepository.findByNombre(autorDto.getNombre());
        if (autorExistente.isPresent()) {
            throw new IllegalStateException("Ya existe un autor con el nombre " + autorDto.getNombre());
        }
        Autor autor = convertirAEntidad(autorDto);
        Autor autorGuardado = autorRepository.save(autor);
        return convertirADto(autorGuardado);
    }

    public AutorDTO obtenerAutor(Long id) {
        return autorRepository.findById(id)
                              .map(this::convertirADto)
                              .orElseThrow(() -> new IllegalStateException("No se encontró el Autor con ID: " + id));
    }

    @Transactional
    public AutorDTO actualizarAutor(Long id, AutorDTO autorDto) {
        return autorRepository.findById(id)
                .map(autorExistente -> {
                    autorExistente.setNombre(autorDto.getNombre());
                    // Aquí puedes agregar la lógica para actualizar otros campos si es necesario
                    return convertirADto(autorRepository.save(autorExistente));
                }).orElseThrow(() -> new IllegalStateException("No se encontró el Autor con ID: " + id));
    }

    @Transactional
    public void eliminarAutor(Long id) {
        if (!autorRepository.existsById(id)) {
            throw new IllegalStateException("No se encontró el Autor con ID: " + id);
        }
        autorRepository.deleteById(id);
    }

    private AutorDTO convertirADto(Autor autor) {
        return new AutorDTO(autor.getId(), autor.getNombre());
    }

    private Autor convertirAEntidad(AutorDTO autorDto) {
        Autor autor = new Autor();
        autor.setId(autorDto.getId());
        autor.setNombre(autorDto.getNombre());
        return autor;
    }
}
