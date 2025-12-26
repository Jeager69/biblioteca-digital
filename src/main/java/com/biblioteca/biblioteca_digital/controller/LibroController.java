package com.biblioteca.biblioteca_digital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.biblioteca_digital.model.Libro;
import com.biblioteca.biblioteca_digital.repository.LibroRepository;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroRepository libroRepository;

    @GetMapping
    public List<Libro> listar() {
        return libroRepository.findAll();
    }

    @PostMapping
    public Libro guardar(@RequestBody Libro libro) {
        return libroRepository.save(libro);
    }
}
