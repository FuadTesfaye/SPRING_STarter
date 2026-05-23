package com.assignment.shipping.application.dto;

public record StockReservedEvent(String orderId, String userId, String productName, int quantity) {}
