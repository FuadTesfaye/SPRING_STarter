package com.example.inventory.application.dto;

public record OrderStockCommand(String orderId, String productId, int quantity, double totalAmount) {
}
