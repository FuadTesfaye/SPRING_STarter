package com.example.auth.presentation;

import java.time.Instant;
import java.util.Map;

public record ApiError(
        Instant timestamp,
        int status,
        String error,
        Map<String, String> validationErrors
) {
}
