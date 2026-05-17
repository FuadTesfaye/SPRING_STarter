package com.school.auth.presentation.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
