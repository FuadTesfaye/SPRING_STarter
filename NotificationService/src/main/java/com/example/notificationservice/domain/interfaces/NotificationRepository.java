package com.example.notificationservice.domain.interfaces;

import com.example.notificationservice.domain.entities.Notification;

public interface NotificationRepository {

    Notification save(Notification notification);
}
