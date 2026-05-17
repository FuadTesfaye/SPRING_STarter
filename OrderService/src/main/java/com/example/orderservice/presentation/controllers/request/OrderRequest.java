package com.example.orderservice.presentation.controllers.request;

public record OrderRequest(Long productId, Integer quantity) {
}
