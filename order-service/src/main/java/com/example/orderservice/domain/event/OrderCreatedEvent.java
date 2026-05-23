package com.example.orderservice.domain.event;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
public class OrderCreatedEvent {
    private UUID orderId;
    private UUID userId;
    private String productSku;
    private int quantity;
    private BigDecimal amount;
    private Instant occurredAt;
    public OrderCreatedEvent() {}
    public OrderCreatedEvent(UUID o, UUID u, String sku, int q, BigDecimal a, Instant t) {
        this.orderId=o; this.userId=u; this.productSku=sku; this.quantity=q; this.amount=a; this.occurredAt=t;
    }
    public UUID getOrderId() { return orderId; } public void setOrderId(UUID v) { this.orderId=v; }
    public UUID getUserId() { return userId; } public void setUserId(UUID v) { this.userId=v; }
    public String getProductSku() { return productSku; } public void setProductSku(String v) { this.productSku=v; }
    public int getQuantity() { return quantity; } public void setQuantity(int v) { this.quantity=v; }
    public BigDecimal getAmount() { return amount; } public void setAmount(BigDecimal v) { this.amount=v; }
    public Instant getOccurredAt() { return occurredAt; } public void setOccurredAt(Instant v) { this.occurredAt=v; }
}
