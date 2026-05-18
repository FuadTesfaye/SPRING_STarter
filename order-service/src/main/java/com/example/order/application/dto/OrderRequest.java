package com.example.order.application.dto;

public record OrderRequest(Long userId, String productId, Integer quantity, Double price) {
}
