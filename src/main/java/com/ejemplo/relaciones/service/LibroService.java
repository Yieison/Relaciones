package com.ejemplo.relaciones.service;

import com.ejemplo.relaciones.model.Libro;
import com.ejemplo.relaciones.repository.LibroRepository;
import com.ejemplo.relaciones.dto.LibroDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public List<LibroDTO> listarLibros() {
        return libroRepository.findAll().stream()
                              .map(this::convertirADto)
                              .collect(Collectors.toList());
    }

    @Transactional
    public LibroDTO guardarLibro(LibroDTO libroDto) {
        Libro libro = convertirAEntidad(libroDto);
        Libro libroGuardado = libroRepository.save(libro);
        return convertirADto(libroGuardado);
    }

    public LibroDTO obtenerLibro(Long id) {
        return libroRepository.findById(id)
                              .map(this::convertirADto)
                              .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado con ID: " + id));
    }

    @Transactional
    public LibroDTO actualizarLibro(Long id, LibroDTO libroDto) {
        return libroRepository.findById(id)
                .map(libroExistente -> {
                    libroExistente.setTitulo(libroDto.getTitulo());
                    // Aquí puedes copiar las propiedades que desees actualizar desde 'libroDto' a 'libroExistente'
                    return convertirADto(libroRepository.save(libroExistente));
                }).orElseThrow(() -> new IllegalArgumentException("Libro no encontrado con ID: " + id));
    }

    @Transactional
    public void eliminarLibro(Long id) {
        if (!libroRepository.existsById(id)) {
            throw new IllegalArgumentException("Libro no encontrado con ID: " + id);
        }
        libroRepository.deleteById(id);
    }

    private LibroDTO convertirADto(Libro libro) {
        // Aquí debes convertir la entidad Libro a LibroDTO.
        // Asegúrate de manejar correctamente las relaciones, como autor y categorías.
        // Este es un ejemplo básico, necesitarás ajustarlo según tu modelo y necesidades.
        LibroDTO libroDto = new LibroDTO();
        libroDto.setId(libro.getId());
        libroDto.setTitulo(libro.getTitulo());
        // Convertir y asignar autor y categorías si es necesario
        return libroDto;
    }

    private Libro convertirAEntidad(LibroDTO libroDto) {
        // Aquí debes convertir LibroDTO a la entidad Libro.
        // Asegúrate de manejar correctamente las relaciones, como autor y categorías.
        // Este es un ejemplo básico, necesitarás ajustarlo según tu modelo y necesidades.
        Libro libro = new Libro();
        libro.setId(libroDto.getId());
        libro.setTitulo(libroDto.getTitulo());
        // Convertir y asignar autor y categorías si es necesario
        return libro;
    }
}
