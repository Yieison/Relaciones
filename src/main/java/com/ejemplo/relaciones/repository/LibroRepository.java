package com.ejemplo.relaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ejemplo.relaciones.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {
}
