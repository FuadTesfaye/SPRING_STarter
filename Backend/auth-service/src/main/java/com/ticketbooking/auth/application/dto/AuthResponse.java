package com.ticketbooking.auth.application.dto;

import java.util.UUID;

public record AuthResponse(
    String token,
    UUID userId,
    String email,
    String fullName,
    String role,
    boolean verified
) {}
