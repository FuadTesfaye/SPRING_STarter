package com.ecommerce.inventory.presentation.dto;

import lombok.Data;

@Data
public class AddStockRequest {
    private String productId;
    private Integer quantity;
}
