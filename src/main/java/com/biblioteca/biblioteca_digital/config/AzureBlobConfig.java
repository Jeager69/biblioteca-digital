package com.biblioteca.biblioteca_digital.config;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AzureBlobConfig {

    private final Environment env;

    public AzureBlobConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public BlobServiceClient blobServiceClient() {

        String connectionString =
                env.getProperty("AZURE_STORAGE_CONNECTION_STRING");

        return new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();
    }
}
