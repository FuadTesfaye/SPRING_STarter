package com.ecommerce.notification.infrastructure.persistence;

import com.ecommerce.notification.domain.model.Notification;
import com.ecommerce.notification.domain.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaNotificationRepository implements NotificationRepository {
    private final SpringDataNotificationRepository repo;

    @Override public Notification save(Notification n) { return repo.save(n); }
    @Override public List<Notification> findAll() { return repo.findAll(); }
    @Override public List<Notification> findByEventType(String type) { return repo.findByEventType(type); }
}
