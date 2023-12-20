package com.ejemplo.relaciones.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroDTO {
    private Long id;
    private String titulo;
    private AutorDTO autor;
    private Set<CategoriaDTO> categorias;
}
