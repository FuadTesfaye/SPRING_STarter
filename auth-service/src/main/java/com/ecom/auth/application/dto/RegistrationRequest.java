package com.ecom.auth.application.dto;

public record RegistrationRequest(
    String username,
    String email,
    String password
) {}
