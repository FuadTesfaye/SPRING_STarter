package com.example.orderservice.application.dto.request;

public record OrderCreateRequest(Long productId, Integer quantity) {
}
