package com.ecom.product.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(UUID id, String name, String description, BigDecimal price, String category, int stockQuantity) {}
