package com.example.orderservice.application.dto.response;

public record ShipmentResponse(String shipmentId, String orderId, String status, String message) {
}
