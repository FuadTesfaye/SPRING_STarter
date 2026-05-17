package com.example.notificationservice.infrastructure.persistence.adapter;

import com.example.notificationservice.domain.entities.Notification;
import com.example.notificationservice.domain.enums.NotificationStatus;
import com.example.notificationservice.infrastructure.persistence.entity.NotificationJpaEntity;

public class NotificationPersistenceMapper {

    public NotificationJpaEntity toEntity(Notification notification) {
        NotificationJpaEntity entity = new NotificationJpaEntity();
        entity.setNotificationId(notification.notificationId());
        entity.setOrderId(notification.orderId());
        entity.setProductId(notification.productId());
        entity.setMessage(notification.message());
        entity.setStatus(notification.status().name());
        return entity;
    }

    public Notification toDomain(NotificationJpaEntity entity) {
        return new Notification(
                entity.getNotificationId(),
                entity.getOrderId(),
                entity.getProductId(),
                entity.getMessage(),
                NotificationStatus.valueOf(entity.getStatus())
        );
    }
}
