package org.example.hydrocore.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import jakarta.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class FirebaseMessagingService {

    private static final Log log = LogFactory.getLog(FirebaseMessagingService.class);
    @Value("${FIREBASE_JSON}")
    private String firebaseJsonBase64;

    @PostConstruct
    public void initialize() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            byte[] decodedBytes = Base64.getDecoder().decode(firebaseJsonBase64);
            try (ByteArrayInputStream serviceAccount = new ByteArrayInputStream(decodedBytes)) {
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
                log.info("Inicializado com sucesso");
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