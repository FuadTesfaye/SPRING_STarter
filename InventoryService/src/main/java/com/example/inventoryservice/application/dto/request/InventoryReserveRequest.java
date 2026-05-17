package com.example.inventoryservice.application.dto.request;

public record InventoryReserveRequest(Long productId, Integer quantity) {
}
