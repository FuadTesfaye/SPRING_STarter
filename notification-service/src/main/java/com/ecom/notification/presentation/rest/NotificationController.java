package com.ecom.notification.presentation.rest;

import com.ecom.notification.application.service.NotificationHandler;
import com.ecom.notification.domain.model.Notification;
import com.ecom.notification.domain.repository.NotificationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {
    private final NotificationHandler notificationHandler;
    private final NotificationRepository notificationRepository;

    public NotificationController(NotificationHandler notificationHandler, NotificationRepository notificationRepository) {
        this.notificationHandler = notificationHandler;
        this.notificationRepository = notificationRepository;
    }

    @PostMapping
    public ResponseEntity<String> createNotification(@RequestBody NotificationRequest request) {
        notificationHandler.processEvent(request.getEventType(), request.getPayload());
        return ResponseEntity.ok("NOTIFICATION_SENT");
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return ResponseEntity.ok(notificationRepository.findAll());
    }

    public static class NotificationRequest {
        private String eventType;
        private String payload;

        public String getEventType() { return eventType; }
        public void setEventType(String eventType) { this.eventType = eventType; }
        public String getPayload() { return payload; }
        public void setPayload(String payload) { this.payload = payload; }
    }
}
