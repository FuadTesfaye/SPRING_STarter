package com.example.inventoryservice.application.dto.response;

public record InventoryUpdateResponse(Long productId, int remainingQuantity, String status, String message) {
}
