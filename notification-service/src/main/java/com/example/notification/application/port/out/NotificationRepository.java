package com.example.notification.application.port.out;

import com.example.notification.domain.model.Notification;

public interface NotificationRepository {

    Notification save(Notification notification);
}
