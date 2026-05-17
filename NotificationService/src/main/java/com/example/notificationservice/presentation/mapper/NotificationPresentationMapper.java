package com.example.notificationservice.presentation.mapper;

import com.example.notificationservice.application.dto.request.SendNotificationRequest;
import com.example.notificationservice.application.dto.response.NotificationResponse;
import com.example.notificationservice.presentation.controllers.request.NotificationRequest;

public class NotificationPresentationMapper {

    public SendNotificationRequest toApplication(NotificationRequest request) {
        return new SendNotificationRequest(request.orderId(), request.productId(), request.message());
    }

    public NotificationResponse toPresentation(NotificationResponse response) {
        return response;
    }
}
