package com.example.order.application.dto;

import com.example.order.domain.model.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderResponse {
    private UUID id;
    private UUID userId;
    private String status;
    private BigDecimal totalAmount;
    private List<OrderItemResponse> items;
    private LocalDateTime createdAt;

    public static OrderResponse fromDomain(Order order) {
        OrderResponse response = new OrderResponse();
        response.id = order.getId();
        response.userId = order.getUserId();
        response.status = order.getStatus();
        response.totalAmount = order.getTotalAmount();
        response.items = order.getItems().stream()
                .map(item -> new OrderItemResponse(
                    item.getProductId(),
                    item.getProductName(),
                    item.getQuantity(),
                    item.getPrice()
                ))
                .collect(Collectors.toList());
        response.createdAt = order.getCreatedAt();
        return response;
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public String getStatus() { return status; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public List<OrderItemResponse> getItems() { return items; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public static class OrderItemResponse {
        private UUID productId;
        private String productName;
        private int quantity;
        private BigDecimal price;

        public OrderItemResponse(UUID productId, String productName, int quantity, BigDecimal price) {
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