package com.example.inventoryservice.presentation.controllers.request;

public record InventoryUpdateRequest(Long productId, Integer quantity) {
}
