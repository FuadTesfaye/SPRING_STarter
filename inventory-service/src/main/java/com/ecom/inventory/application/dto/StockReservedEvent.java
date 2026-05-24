package com.ecom.inventory.application.dto;

import java.util.UUID;

public record StockReservedEvent(
    UUID orderId,
    String status
) {}
