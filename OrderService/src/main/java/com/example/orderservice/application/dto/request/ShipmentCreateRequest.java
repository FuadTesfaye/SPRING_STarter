package com.example.orderservice.application.dto.request;

public record ShipmentCreateRequest(String orderId, Long productId, Integer quantity) {
}
