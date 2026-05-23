package com.ticketbooking.notification.infrastructure.store;

import com.ticketbooking.notification.domain.model.InAppNotification;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class NotificationStore {

    private final Map<String, InAppNotification> store = new ConcurrentHashMap<>();

    public InAppNotification add(String email, String title, String message) {
        InAppNotification n = new InAppNotification(
            UUID.randomUUID().toString(), email, title, message, false, Instant.now()
        );
        store.put(n.id(), n);
        return n;
    }

    public List<InAppNotification> findByEmail(String email) {
        return store.values().stream()
            .filter(n -> n.email().equalsIgnoreCase(email))
            .sorted((a, b) -> b.createdAt().compareTo(a.createdAt()))
            .collect(Collectors.toList());
    }

    public void markRead(String email) {
        List<String> keys = store.values().stream()
            .filter(n -> n.email().equalsIgnoreCase(email) && !n.read())
            .map(InAppNotification::id)
            .toList();
        keys.forEach(id -> {
            InAppNotification old = store.get(id);
            store.put(id, new InAppNotification(old.id(), old.email(), old.title(), old.message(), true, old.createdAt()));
        });
    }

    public long countUnread(String email) {
        return store.values().stream()
            .filter(n -> n.email().equalsIgnoreCase(email) && !n.read())
            .count();
    }
}
