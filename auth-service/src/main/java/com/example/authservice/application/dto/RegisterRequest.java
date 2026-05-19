package com.example.authservice.application.dto;

import lombok.Data;

@Data
public class RegisterRequest {

    private String username;
    private String password;
}