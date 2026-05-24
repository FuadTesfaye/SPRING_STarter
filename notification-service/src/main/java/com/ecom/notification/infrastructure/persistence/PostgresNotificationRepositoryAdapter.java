package com.ecom.notification.infrastructure.persistence;

import com.ecom.notification.domain.model.Notification;
import com.ecom.notification.domain.repository.NotificationRepository;
import org.springframework.stereotype.Component;

@Component
public class PostgresNotificationRepositoryAdapter implements NotificationRepository {
    private final JpaNotificationRepository repository;

    public PostgresNotificationRepositoryAdapter(JpaNotificationRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Notification notification) {
        NotificationEntity entity = new NotificationEntity(
                notification.getId(),
                notification.getEventType(),
                notification.getPayload(),
                notification.getReceivedAt()
        );
        repository.save(entity);
    }

    @Override
    public java.util.List<Notification> findAll() {
        return repository.findAll().stream()
                .map(entity -> new Notification(entity.getId(), entity.getEventType(), entity.getPayload(), entity.getReceivedAt()))
                .collect(java.util.stream.Collectors.toList());
    }
}
