package com.example.shipmentservice.application.dto.request;

public record ShipmentCreateRequest(String orderId, Long productId, Integer quantity) {
}
