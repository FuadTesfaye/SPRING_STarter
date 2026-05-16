package com.example.events;

import java.time.Instant;

public record UserRegisteredEvent(
        Long userId,
        String username,
        String email,
        Instant occurredAt
) {
}
