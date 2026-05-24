package com.ecom.inventory.application.dto;

import java.util.List;
import java.util.UUID;

public record OrderCreatedEvent(
    UUID orderId,
    List<OrderItemDetail> items
) {}

