package com.example.inventoryservice.application.dto.response;

public record InventoryStatusResponse(Long productId, int availableQuantity, String status) {
}
