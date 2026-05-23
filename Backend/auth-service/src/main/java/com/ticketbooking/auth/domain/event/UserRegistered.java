package com.ticketbooking.auth.domain.event;

import java.util.UUID;

public record UserRegistered(
    UUID userId,
    String email,
    String fullName
) {
    public static UserRegistered of(UUID userId, String email, String fullName) {
        return new UserRegistered(userId, email, fullName);
    }
}
