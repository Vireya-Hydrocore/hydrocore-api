package org.example.hydrocore.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@Service
public class FirebaseMessagingService {

    @Value("${FIREBASE_JSON}")
    private String FIREBASE_JSON;

    @PostConstruct
    public void initialize() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            String json = Files.readString(Paths.get(FIREBASE_JSON));

            Map<String, String> env = System.getenv();
            for (Map.Entry<String, String> entry : env.entrySet()) {
                String placeholder = "${" + entry.getKey() + "}";
                String value = entry.getValue();
                if (value != null) {
                    value = value.replace("\\n", "\n");
                }
                json = json.replace(placeholder, value != null ? value : "");
            }

            try (ByteArrayInputStream serviceAccount = new ByteArrayInputStream(json.getBytes())) {
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
            }
        }
    }

    public void enviarNotificacao(String titulo, String corpo) {
        try {
            Message message = Message.builder()
                    .setNotification(Notification.builder()
                            .setTitle(titulo)
                            .setBody(corpo)
                            .build())
                    .setTopic("avisos-gerais")
                    .build();

            FirebaseMessaging.getInstance().send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
