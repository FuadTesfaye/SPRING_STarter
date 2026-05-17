package com.example.apigateway.presentation.advice;

public record ErrorResponse(String timestamp, int status, String error, String message) {
}
