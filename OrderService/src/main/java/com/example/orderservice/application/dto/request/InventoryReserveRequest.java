package com.example.orderservice.application.dto.request;

public record InventoryReserveRequest(Long productId, Integer quantity) {
}
