package com.ecom.auth.application.dto;

public record LoginRequest(
    String username,
    String password
) {}
