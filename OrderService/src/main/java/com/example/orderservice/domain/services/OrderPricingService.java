package com.example.orderservice.domain.services;

import java.util.Map;

public class OrderPricingService {

    private static final Map<Long, Double> PRODUCT_PRICES = Map.of(
            1L, 25.0,
            2L, 40.0,
            3L, 65.0
    );

    public double calculateAmount(Long productId, int quantity) {
        Double unitPrice = PRODUCT_PRICES.get(productId);
        if (unitPrice == null) {
            throw new IllegalArgumentException("Unsupported productId: " + productId);
        }
        return unitPrice * quantity;
    }
}
