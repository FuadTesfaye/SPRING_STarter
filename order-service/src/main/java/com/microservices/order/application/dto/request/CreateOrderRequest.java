package com.microservices.order.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

/**
 * DTO: CreateOrderRequest
 * 
 * WHAT: Data needed to create a new order
 * WHY: Validates input format before reaching domain layer
 * 
 * VALIDATION:
 * - userId: Must not be blank
 * - items: Must have at least one item
 * - Each item: Must have valid productId, quantity, and price
 */
public class CreateOrderRequest {
    
    @NotBlank(message = "User ID is required")
    private String userId;
    
    @NotEmpty(message = "Order must have at least one item")
    @Valid  // Validates each item in the list
    private List<OrderItemRequest> items;
    
    public CreateOrderRequest() {}
    
    public CreateOrderRequest(String userId, List<OrderItemRequest> items) {
        this.userId = userId;
        this.items = items;
    }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public List<OrderItemRequest> getItems() { return items; }
    public void setItems(List<OrderItemRequest> items) { this.items = items; }
    
    /**
     * Nested DTO for each item in the order
     */
    public static class OrderItemRequest {
        
        @NotBlank(message = "Product ID is required")
        private String productId;
        
        @NotBlank(message = "Product name is required")
        private String productName;
        
        @Positive(message = "Quantity must be positive")
        private int quantity;
        
        @NotNull(message = "Price is required")
        @Positive(message = "Price must be positive")
        private BigDecimal price;
        
        public OrderItemRequest() {}
        
        public OrderItemRequest(String productId, String productName, int quantity, BigDecimal price) {
            this.productId = productId;
            this.productName = productName;
            this.quantity = quantity;
            this.price = price;
        }
        
        public String getProductId() { return productId; }
        public void setProductId(String productId) { this.productId = productId; }
        
        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }
        
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        
        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }
    }
}
