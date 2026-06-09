package com.fatec.glab.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.credentials.base64}")
    private String firebaseCredentialsBase64;

    @PostConstruct
    public void init() {
        if (firebaseCredentialsBase64 == null || firebaseCredentialsBase64.trim().isEmpty()) {
            System.out.println("Firebase credentials not provided. Skipping initialization.");
            return;
        }
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(firebaseCredentialsBase64.trim());
            
            InputStream serviceAccount = new ByteArrayInputStream(decodedBytes);

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
            System.out.println("Firebase initialized!");

        } catch (Exception e) {
            System.err.println("Erro ao inicializar Firebase: " + e.getMessage());
        }
    }
}