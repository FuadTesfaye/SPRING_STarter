package com.example.orderservice.infrastructure.persistence;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
@Entity @Table(name="orders")
public class OrderEntity {
    @Id private UUID id;
    private UUID userId;
    private String productSku;
    private int quantity;
    private BigDecimal amount;
    private Instant createdAt;
    public UUID getId() { return id; } public void setId(UUID v) { this.id=v; }
    public UUID getUserId() { return userId; } public void setUserId(UUID v) { this.userId=v; }
    public String getProductSku() { return productSku; } public void setProductSku(String v) { this.productSku=v; }
    public int getQuantity() { return quantity; } public void setQuantity(int v) { this.quantity=v; }
    public BigDecimal getAmount() { return amount; } public void setAmount(BigDecimal v) { this.amount=v; }
    public Instant getCreatedAt() { return createdAt; } public void setCreatedAt(Instant v) { this.createdAt=v; }
}
