package com.example.notificationservice.presentation.controllers;

import com.example.notificationservice.application.dto.response.NotificationResponse;
import com.example.notificationservice.application.usecases.SendNotificationService;
import com.example.notificationservice.presentation.controllers.request.NotificationRequest;
import com.example.notificationservice.presentation.mapper.NotificationPresentationMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final SendNotificationService sendNotificationUseCase;
    private final NotificationPresentationMapper notificationPresentationMapper;

    public NotificationController(SendNotificationService sendNotificationUseCase) {
        this.sendNotificationUseCase = sendNotificationUseCase;
        this.notificationPresentationMapper = new NotificationPresentationMapper();
    }

    @PostMapping
    public NotificationResponse send(@RequestBody NotificationRequest request) {
        return notificationPresentationMapper.toPresentation(
                sendNotificationUseCase.execute(notificationPresentationMapper.toApplication(request))
        );
    }
}
