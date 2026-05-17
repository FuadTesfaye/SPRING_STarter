package com.example.shipmentservice.presentation.controllers.request;

public record ShipmentRequest(String orderId, Long productId, Integer quantity) {
}
