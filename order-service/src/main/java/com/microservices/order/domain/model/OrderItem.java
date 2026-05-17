package com.microservices.order.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public final class OrderItem {
    private final String productId;
    private final String productName;
    private final int quantity;
    private final BigDecimal price;
    
    public OrderItem(String productId, String productName, int quantity, BigDecimal price) {
        if (productId == null || productId.isBlank()) {
            throw new IllegalArgumentException("Product ID required");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        this.productId = productId;
        this.productName = productName != null ? productName : "";
        this.quantity = quantity;
        this.price = price;
    }
    
    public BigDecimal getTotalPrice() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
    
    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public BigDecimal getPrice() { return price; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem item = (OrderItem) o;
        return productId.equals(item.productId);
    }
    
    @Override
    public int hashCode() { return Objects.hash(productId); }
}
