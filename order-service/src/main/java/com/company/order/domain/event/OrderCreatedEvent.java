package com.company.order.domain.event;

import java.math.BigDecimal;

public class OrderCreatedEvent {
    private Long orderId;
    private String productId;
    private Integer quantity;
    private BigDecimal pricePerUnit;

    public OrderCreatedEvent(Long orderId, String productId, Integer quantity, BigDecimal pricePerUnit) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
    }

    public Long getOrderId() { return orderId; }
    public String getProductId() { return productId; }
    public Integer getQuantity() { return quantity; }
    public BigDecimal getPricePerUnit() { return pricePerUnit; }
}
