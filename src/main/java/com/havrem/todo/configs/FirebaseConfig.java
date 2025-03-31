package com.havrem.todo.configs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

import java.io.File;
import java.io.FileInputStream;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() {
        String path = System.getenv("FIREBASE_CONFIG_PATH");

        if (path == null || path.isBlank()) {
            System.out.println("No FIREBASE_CONFIG_PATH set, skipping Firebase init");
            return;
        }

        File file = new File(path);

        try (FileInputStream serviceAccount = new FileInputStream(file)) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}