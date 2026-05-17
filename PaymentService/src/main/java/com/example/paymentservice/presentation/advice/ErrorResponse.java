package com.example.paymentservice.presentation.advice;

public record ErrorResponse(String timestamp, int status, String error, String message) {
}
