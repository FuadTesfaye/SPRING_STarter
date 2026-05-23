package com.example.inventoryservice.domain.event;
import java.time.Instant; import java.util.UUID;
public class StockFailedEvent {
    private UUID orderId; private String sku; private String reason; private Instant occurredAt;
    public StockFailedEvent() {}
    public StockFailedEvent(UUID o, String s, String r, Instant t) {
        orderId=o; sku=s; reason=r; occurredAt=t;
    }
    public UUID getOrderId(){return orderId;} public void setOrderId(UUID v){orderId=v;}
    public String getSku(){return sku;} public void setSku(String v){sku=v;}
    public String getReason(){return reason;} public void setReason(String v){reason=v;}
    public Instant getOccurredAt(){return occurredAt;} public void setOccurredAt(Instant v){occurredAt=v;}
}
