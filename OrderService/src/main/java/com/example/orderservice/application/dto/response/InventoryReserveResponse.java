package com.example.orderservice.application.dto.response;

public record InventoryReserveResponse(Long productId, int remainingQuantity, String status, String message) {
}
