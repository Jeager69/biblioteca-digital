package com.biblioteca.biblioteca_digital.config;

import com.azure.identity.DefaultAzureCredentialBuilder;
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

        String accountName = env.getProperty("AZURE_STORAGE_ACCOUNT_NAME");

        String endpoint = "https://" + accountName + ".blob.core.windows.net";

        return new BlobServiceClientBuilder()
                .endpoint(endpoint)
                .credential(new DefaultAzureCredentialBuilder().build())
                .buildClient();
    }
}
