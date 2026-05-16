package com.example.notification.presentation.controller;

import com.example.notification.infrastructure.persistence.SpringDataNotificationRepository;
import com.example.notification.infrastructure.persistence.NotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final SpringDataNotificationRepository notificationRepository;

    public NotificationController(SpringDataNotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<NotificationResponse> getNotifications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return notificationRepository.findAll(PageRequest.of(page, size))
                .map(this::toResponse);
    }

    private NotificationResponse toResponse(NotificationEntity entity) {
        return new NotificationResponse(
                entity.getId(),
                entity.getEventType(),
                entity.getMessage(),
                entity.getDetails(),
                entity.getCreatedAt()
        );
    }

    public record NotificationResponse(
            Long id,
            String eventType,
            String message,
            String details,
            java.time.Instant createdAt
    ) {
    }
}
