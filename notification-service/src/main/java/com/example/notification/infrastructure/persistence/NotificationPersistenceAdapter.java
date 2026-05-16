package com.example.notification.infrastructure.persistence;

import com.example.notification.application.port.out.NotificationRepository;
import com.example.notification.domain.model.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationPersistenceAdapter implements NotificationRepository {

    private final SpringDataNotificationRepository springDataNotificationRepository;

    public NotificationPersistenceAdapter(SpringDataNotificationRepository springDataNotificationRepository) {
        this.springDataNotificationRepository = springDataNotificationRepository;
    }

    @Override
    public Notification save(Notification notification) {
        NotificationEntity entity = toPersistence(notification);
        NotificationEntity saved = springDataNotificationRepository.save(entity);
        return toDomain(saved);
    }

    private Notification toDomain(NotificationEntity entity) {
        return new Notification(
                entity.getId(),
                entity.getEventType(),
                entity.getMessage(),
                entity.getDetails(),
                entity.getCreatedAt()
        );
    }

    private NotificationEntity toPersistence(Notification notification) {
        return new NotificationEntity(
                notification.getId(),
                notification.getEventType(),
                notification.getMessage(),
                notification.getDetails(),
                notification.getCreatedAt()
        );
    }
}
