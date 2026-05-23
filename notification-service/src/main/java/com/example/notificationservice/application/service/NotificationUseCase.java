package com.example.notificationservice.application.service;
import com.example.notificationservice.application.port.NotifierPort;
import com.example.notificationservice.domain.model.Notification;
import org.springframework.stereotype.Service;
import java.time.Instant;
@Service
public class NotificationUseCase {
    private final NotifierPort notifier;
    public NotificationUseCase(NotifierPort n) { this.notifier = n; }
    public void handle(String type, String payload) {
        notifier.notify(new Notification(type, payload, Instant.now()));
    }
}
