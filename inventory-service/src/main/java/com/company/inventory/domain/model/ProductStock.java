package com.company.inventory.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductStock {
    private String productId;
    private Integer availableQuantity;

    public boolean hasEnoughStock(int requestedQuantity) {
        return availableQuantity >= requestedQuantity;
    }

    public void deductStock(int quantity) {
        if (!hasEnoughStock(quantity)) {
            throw new RuntimeException("Insufficient stock for product: " + productId);
        }
        this.availableQuantity -= quantity;
    }
}
