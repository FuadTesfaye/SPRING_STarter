package com.example.shipmentservice.application.dto.response;

public record ShipmentResponse(String shipmentId, String orderId, String status, String message) {
}
