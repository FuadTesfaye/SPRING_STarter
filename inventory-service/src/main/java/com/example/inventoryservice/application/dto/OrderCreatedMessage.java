package com.example.inventoryservice.application.dto;
import java.math.BigDecimal; import java.util.UUID;
public class OrderCreatedMessage {
    public UUID orderId; public UUID userId; public String productSku;
    public int quantity; public BigDecimal amount;
}
