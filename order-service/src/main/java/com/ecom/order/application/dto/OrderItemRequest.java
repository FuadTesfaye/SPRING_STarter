package com.ecom.order.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemRequest(
    UUID productId,
    String productName,
    int quantity,
    BigDecimal price
) {}
