package com.example.orderservice.application.interfaces;

import com.example.orderservice.application.dto.request.SendNotificationRequest;
import com.example.orderservice.application.dto.response.NotificationResponse;

public interface NotificationGateway {

    NotificationResponse sendNotification(SendNotificationRequest request);
}
