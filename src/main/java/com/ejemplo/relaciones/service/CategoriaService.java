package com.ejemplo.relaciones.service;

import com.ejemplo.relaciones.model.Categoria;
import com.ejemplo.relaciones.repository.CategoriaRepository;
import com.ejemplo.relaciones.dto.CategoriaDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaDTO> listarCategorias() {
        return categoriaRepository.findAll().stream()
                                  .map(this::convertirADto)
                                  .collect(Collectors.toList());
    }

    @Transactional
    public CategoriaDTO guardarCategoria(CategoriaDTO categoriaDto) {
        Categoria categoria = convertirAEntidad(categoriaDto);
        Categoria categoriaGuardada = categoriaRepository.save(categoria);
        return convertirADto(categoriaGuardada);
    }

    public CategoriaDTO obtenerCategoria(Long id) {
        return categoriaRepository.findById(id)
                                  .map(this::convertirADto)
                                  .orElseThrow(() -> new NoSuchElementException("No se encontró la Categoría con ID: " + id));
    }

    @Transactional
    public CategoriaDTO actualizarCategoria(Long id, CategoriaDTO categoriaDto) {
        return categoriaRepository.findById(id)
                .map(categoriaExistente -> {
                    categoriaExistente.setNombre(categoriaDto.getNombre());
                    // Aquí puedes copiar las propiedades que deseas actualizar de 'categoriaDto' a 'categoriaExistente'
                    return convertirADto(categoriaRepository.save(categoriaExistente));
                }).orElseThrow(() -> new NoSuchElementException("No se encontró la Categoría con ID: " + id));
    }

    @Transactional
    public void eliminarCategoria(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new NoSuchElementException("No se encontró la Categoría con ID: " + id);
        }
        categoriaRepository.deleteById(id);
    }

    private CategoriaDTO convertirADto(Categoria categoria) {
        return new CategoriaDTO(categoria.getId(), categoria.getNombre());
    }

    private Categoria convertirAEntidad(CategoriaDTO categoriaDto) {
        Categoria categoria = new Categoria();
        categoria.setId(categoriaDto.getId());
        categoria.setNombre(categoriaDto.getNombre());
        return categoria;
    }
}
