package com.eventdriven.orderservice.application.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderCreatedEvent implements Serializable {
    private Long orderId;
    private Long userId;
    private String product;
    private Integer quantity;
    private BigDecimal price;
    private LocalDateTime timestamp;

    public OrderCreatedEvent() {}

    public OrderCreatedEvent(Long orderId, Long userId, String product, Integer quantity, BigDecimal price, LocalDateTime timestamp) {
        this.orderId = orderId;
        this.userId = userId;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.timestamp = timestamp;
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getProduct() { return product; }
    public void setProduct(String product) { this.product = product; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long orderId;
        private Long userId;
        private String product;
        private Integer quantity;
        private BigDecimal price;
        private LocalDateTime timestamp;

        public Builder orderId(Long orderId) { this.orderId = orderId; return this; }
        public Builder userId(Long userId) { this.userId = userId; return this; }
        public Builder product(String product) { this.product = product; return this; }
        public Builder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public Builder price(BigDecimal price) { this.price = price; return this; }
        public Builder timestamp(LocalDateTime timestamp) { this.timestamp = timestamp; return this; }
        public OrderCreatedEvent build() {
            return new OrderCreatedEvent(orderId, userId, product, quantity, price, timestamp);
        }
    }
}