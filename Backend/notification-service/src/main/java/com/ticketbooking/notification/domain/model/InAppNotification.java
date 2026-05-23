package com.ticketbooking.notification.domain.model;

import java.time.Instant;

public record InAppNotification(
    String id,
    String email,
    String title,
    String message,
    boolean read,
    Instant createdAt
) {}
