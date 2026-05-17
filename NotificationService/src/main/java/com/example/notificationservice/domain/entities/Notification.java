package com.example.notificationservice.domain.entities;

import com.example.notificationservice.domain.enums.NotificationStatus;

public class Notification {

    private final String notificationId;
    private final String orderId;
    private final Long productId;
    private final String message;
    private final NotificationStatus status;

    public Notification(
            String notificationId,
            String orderId,
            Long productId,
            String message,
            NotificationStatus status
    ) {
        this.notificationId = notificationId;
        this.orderId = orderId;
        this.productId = productId;
        this.message = message;
        this.status = status;
    }

    public String notificationId() {
        return notificationId;
    }

    public String orderId() {
        return orderId;
    }

    public Long productId() {
        return productId;
    }

    public String message() {
        return message;
    }

    public NotificationStatus status() {
        return status;
    }
}
