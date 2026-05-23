package com.ticketbooking.notification.application.dto;
public record StockReservedEvent(String orderId, String userId, String productName, int quantity) {}
