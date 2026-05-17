package com.eventdriven.shippingservice.application.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class StockReservedEvent implements Serializable {
    private Long orderId;
    private String product;
    private Integer quantity;
    private LocalDateTime timestamp;

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public String getProduct() { return product; }
    public void setProduct(String product) { this.product = product; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}