package com.example.inventoryservice.presentation.advice;

public record ErrorResponse(String timestamp, int status, String error, String message) {
}
