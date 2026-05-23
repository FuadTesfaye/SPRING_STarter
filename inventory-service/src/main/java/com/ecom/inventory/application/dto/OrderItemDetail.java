package com.ecom.inventory.application.dto;

import java.util.UUID;

public record OrderItemDetail(
    UUID productId,
    int quantity
) {}
