package com.example.paymentservice.domain.event;
import java.time.Instant; import java.util.UUID;
public class PaymentFailedEvent {
    private UUID orderId; private String reason; private Instant occurredAt;
    public PaymentFailedEvent() {}
    public PaymentFailedEvent(UUID o, String r, Instant t) { orderId=o; reason=r; occurredAt=t; }
    public UUID getOrderId(){return orderId;} public void setOrderId(UUID v){orderId=v;}
    public String getReason(){return reason;} public void setReason(String v){reason=v;}
    public Instant getOccurredAt(){return occurredAt;} public void setOccurredAt(Instant v){occurredAt=v;}
}
