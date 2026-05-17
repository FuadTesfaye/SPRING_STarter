package com.example.notificationservice.application.usecases;

import com.example.notificationservice.application.dto.request.SendNotificationRequest;
import com.example.notificationservice.application.dto.response.NotificationResponse;
import com.example.notificationservice.application.usecases.SendNotificationService;
import com.example.notificationservice.domain.entities.Notification;
import com.example.notificationservice.domain.interfaces.NotificationRepository;
import com.example.notificationservice.domain.services.NotificationDomainService;

public class SendNotificationUseCase implements SendNotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationDomainService notificationDomainService;

    public SendNotificationUseCase(
            NotificationRepository notificationRepository,
            NotificationDomainService notificationDomainService
    ) {
        this.notificationRepository = notificationRepository;
        this.notificationDomainService = notificationDomainService;
    }

    public NotificationResponse execute(SendNotificationRequest request) {
        validate(request);
        Notification notification = notificationRepository.save(
                notificationDomainService.createNotification(request.orderId(), request.productId(), request.message())
        );
        return new NotificationResponse(
                notification.notificationId(),
                notification.status().name(),
                "Notification sent successfully"
        );
    }

    private void validate(SendNotificationRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request body is required");
        }
        if (request.orderId() == null || request.orderId().isBlank()) {
            throw new IllegalArgumentException("orderId is required");
        }
        if (request.productId() == null) {
            throw new IllegalArgumentException("productId is required");
        }
        if (request.message() == null || request.message().isBlank()) {
            throw new IllegalArgumentException("message is required");
        }
    }
}
