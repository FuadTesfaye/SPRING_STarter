package com.ticketbooking.notification.application.dto;
public record ShipmentCreatedEvent(String shipmentId, String orderId, String userId, String productName, int quantity, String trackingNumber) {}
