package com.biblioteca.biblioteca_digital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.biblioteca_digital.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {
}
