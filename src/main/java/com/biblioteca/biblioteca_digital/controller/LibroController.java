package com.biblioteca.biblioteca_digital.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;

import com.biblioteca.biblioteca_digital.model.Libro;
import com.biblioteca.biblioteca_digital.repository.LibroRepository;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private BlobServiceClient blobServiceClient;

    private static final String CONTAINER_NAME = "portadas-libros";

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
    // SUBIR PORTADA A AZURE BLOB
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
            // Obtener contenedor
            BlobContainerClient containerClient =
                    blobServiceClient.getBlobContainerClient(CONTAINER_NAME);

            // Crear nombre único
            String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

            // Crear blob
            BlobClient blobClient = containerClient.getBlobClient(fileName);

            // Subir archivo
            blobClient.upload(file.getInputStream(), file.getSize(), true);

            // Guardar URL en BD
            String urlPortada = blobClient.getBlobUrl();
            libro.setPortadaUrl(urlPortada);
            libroRepository.save(libro);

            return ResponseEntity.ok(libro);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al subir la portada: " + e.getMessage());
        }
    }
}
