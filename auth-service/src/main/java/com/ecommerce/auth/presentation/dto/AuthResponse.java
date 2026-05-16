package com.ecommerce.auth.presentation.dto;

import lombok.*;

@Data @Builder
public class AuthResponse {
    private String message;
    private String userId;
    private String token;
}
