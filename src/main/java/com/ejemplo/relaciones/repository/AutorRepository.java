package com.ejemplo.relaciones.repository;

import com.ejemplo.relaciones.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    // Método para buscar un autor por su nombre
    Optional<Autor> findByNombre(String nombre);
}
