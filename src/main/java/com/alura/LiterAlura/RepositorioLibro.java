package com.alura.LiterAlura;

import com.alura.LiterAlura.modelos.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioLibro extends JpaRepository<Libro, Long> {
}
