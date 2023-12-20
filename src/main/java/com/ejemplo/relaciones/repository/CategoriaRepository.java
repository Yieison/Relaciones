package com.ejemplo.relaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ejemplo.relaciones.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
