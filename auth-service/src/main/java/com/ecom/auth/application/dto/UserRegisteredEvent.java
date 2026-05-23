package com.ecom.auth.application.dto;

import java.util.UUID;

public record UserRegisteredEvent(
    UUID userId,
    String username,
    String email
) {}
