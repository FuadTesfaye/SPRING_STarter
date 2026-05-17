package com.example.notificationservice.infrastructure.persistence.adapter;

import com.example.notificationservice.domain.entities.Notification;
import com.example.notificationservice.domain.interfaces.NotificationRepository;
import com.example.notificationservice.infrastructure.persistence.repository.SpringDataNotificationRepository;

public class NotificationPersistenceAdapter implements NotificationRepository {

    private final SpringDataNotificationRepository springDataNotificationRepository;
    private final NotificationPersistenceMapper notificationPersistenceMapper;

    public NotificationPersistenceAdapter(
            SpringDataNotificationRepository springDataNotificationRepository,
            NotificationPersistenceMapper notificationPersistenceMapper
    ) {
        this.springDataNotificationRepository = springDataNotificationRepository;
        this.notificationPersistenceMapper = notificationPersistenceMapper;
    }

    @Override
    public Notification save(Notification notification) {
        return notificationPersistenceMapper.toDomain(
                springDataNotificationRepository.save(notificationPersistenceMapper.toEntity(notification))
        );
    }
}
