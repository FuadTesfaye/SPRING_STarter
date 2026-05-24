package com.ecom.notification.domain.repository;

import com.ecom.notification.domain.model.Notification;
import java.util.List;

public interface NotificationRepository {
    void save(Notification notification);
    List<Notification> findAll();
}
