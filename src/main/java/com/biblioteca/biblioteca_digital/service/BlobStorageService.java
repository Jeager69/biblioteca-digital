package com.biblioteca.biblioteca_digital.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;

@Service
public class BlobStorageService {

    private final BlobContainerClient containerClient;

    public BlobStorageService(BlobServiceClient blobServiceClient) {
        this.containerClient =
                blobServiceClient.getBlobContainerClient("portadas-libros");

        if (!this.containerClient.exists()) {
            this.containerClient.create();
        }
    }

    public String subirPortada(MultipartFile archivo, Long libroId) throws IOException {

        String nombreArchivo =
                "libro-" + libroId + "-" + UUID.randomUUID() + ".jpg";

        BlobClient blobClient = containerClient.getBlobClient(nombreArchivo);

        blobClient.upload(
                archivo.getInputStream(),
                archivo.getSize(),
                true
        );

        return blobClient.getBlobUrl(); // URL pública interna
    }
}
