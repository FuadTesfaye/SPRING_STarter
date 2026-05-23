package com.example.paymentservice.domain.event;
import java.math.BigDecimal; import java.time.Instant; import java.util.UUID;
public class PaymentCompletedEvent {
    private UUID paymentId; private UUID orderId; private BigDecimal amount; private Instant occurredAt;
    public PaymentCompletedEvent() {}
    public PaymentCompletedEvent(UUID p, UUID o, BigDecimal a, Instant t) {
        this.paymentId=p; this.orderId=o; this.amount=a; this.occurredAt=t;
    }
    public UUID getPaymentId(){return paymentId;} public void setPaymentId(UUID v){paymentId=v;}
    public UUID getOrderId(){return orderId;}     public void setOrderId(UUID v){orderId=v;}
    public BigDecimal getAmount(){return amount;} public void setAmount(BigDecimal v){amount=v;}
    public Instant getOccurredAt(){return occurredAt;} public void setOccurredAt(Instant v){occurredAt=v;}
}
