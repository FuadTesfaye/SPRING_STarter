package com.ecommerce.notification.infrastructure.persistence;

import com.ecommerce.notification.domain.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SpringDataNotificationRepository extends JpaRepository<Notification, String> {
    List<Notification> findByEventType(String eventType);
}
