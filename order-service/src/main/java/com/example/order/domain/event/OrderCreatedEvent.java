package com.example.order.domain.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderCreatedEvent {
    private UUID orderId;
    private UUID userId;
    private BigDecimal totalAmount;
    private List<OrderItemDetail> items;
    private LocalDateTime timestamp;

    public OrderCreatedEvent(UUID orderId, UUID userId, BigDecimal totalAmount, List<OrderItemDetail> items) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.items = items;
        this.timestamp = LocalDateTime.now();
    }

    public UUID getOrderId() { return orderId; }
    public UUID getUserId() { return userId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public List<OrderItemDetail> getItems() { return items; }
    public LocalDateTime getTimestamp() { return timestamp; }

    public static class OrderItemDetail {
        private UUID productId;
        private String productName;
        private int quantity;
        private BigDecimal price;

        public OrderItemDetail(UUID productId, String productName, int quantity, BigDecimal price) {
            this.productId = productId;
            this.productName = productName;
            this.quantity = quantity;
            this.price = price;
        }

        public UUID getProductId() { return productId; }
        public String getProductName() { return productName; }
        public int getQuantity() { return quantity; }
        public BigDecimal getPrice() { return price; }
    }
}