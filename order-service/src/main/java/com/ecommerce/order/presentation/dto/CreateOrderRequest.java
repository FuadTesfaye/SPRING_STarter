package com.ecommerce.order.presentation.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateOrderRequest {
    private String userId;
    private String productId;
    private Integer quantity;
    private BigDecimal amount;
    private String shippingAddress;
}
