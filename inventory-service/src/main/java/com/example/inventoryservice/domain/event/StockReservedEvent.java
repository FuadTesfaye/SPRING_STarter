package com.example.inventoryservice.domain.event;
import java.time.Instant; import java.util.UUID;
public class StockReservedEvent {
    private UUID orderId; private String sku; private int quantity; private Instant occurredAt;
    public StockReservedEvent() {}
    public StockReservedEvent(UUID o, String s, int q, Instant t) {
        orderId=o; sku=s; quantity=q; occurredAt=t;
    }
    public UUID getOrderId(){return orderId;} public void setOrderId(UUID v){orderId=v;}
    public String getSku(){return sku;} public void setSku(String v){sku=v;}
    public int getQuantity(){return quantity;} public void setQuantity(int v){quantity=v;}
    public Instant getOccurredAt(){return occurredAt;} public void setOccurredAt(Instant v){occurredAt=v;}
}
