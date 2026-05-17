package com.example.notificationservice.application.usecases;

import com.example.notificationservice.application.dto.request.SendNotificationRequest;
import com.example.notificationservice.application.dto.response.NotificationResponse;

public interface SendNotificationService {

    NotificationResponse execute(SendNotificationRequest request);
}
