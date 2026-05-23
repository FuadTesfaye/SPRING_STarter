package com.ticketbooking.notification.application.dto;
public record StockFailedEvent(String orderId, String userId, String productName, String reason) {}
