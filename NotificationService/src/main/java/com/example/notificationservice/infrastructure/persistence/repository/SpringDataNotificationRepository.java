package com.example.notificationservice.infrastructure.persistence.repository;

import com.example.notificationservice.infrastructure.persistence.entity.NotificationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataNotificationRepository extends JpaRepository<NotificationJpaEntity, String> {
}
