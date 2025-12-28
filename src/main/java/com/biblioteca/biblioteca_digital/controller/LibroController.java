package com.biblioteca.biblioteca_digital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.biblioteca.biblioteca_digital.model.Libro;
import com.biblioteca.biblioteca_digital.repository.LibroRepository;
import com.biblioteca.biblioteca_digital.service.BlobStorageService;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private BlobStorageService blobStorageService;

    // =========================
    // LISTAR LIBROS
    // =========================
    @GetMapping
    public List<Libro> listar() {
        return libroRepository.findAll();
    }

    // =========================
    // CREAR LIBRO
    // =========================
    @PostMapping
    public Libro guardar(@RequestBody Libro libro) {
        return libroRepository.save(libro);
    }

    // =========================
    // SUBIR PORTADA
    // =========================
    @PostMapping("/{id}/portada")
    public ResponseEntity<?> subirPortada(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        Libro libro = libroRepository.findById(id).orElse(null);

        if (libro == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            String urlPortada = blobStorageService.subirPortada(file, id);
            libro.setPortadaUrl(urlPortada);
            libroRepository.save(libro);

            return ResponseEntity.ok(libro);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al subir portada: " + e.getMessage());
        }
    }
}
