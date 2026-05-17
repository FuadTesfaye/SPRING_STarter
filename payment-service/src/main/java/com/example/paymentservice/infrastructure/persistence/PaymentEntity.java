package com.example.paymentservice.infrastructure.persistence;
import com.example.paymentservice.domain.model.Payment;
import jakarta.persistence.*;
import java.math.BigDecimal; import java.time.Instant; import java.util.UUID;
@Entity @Table(name="payments")
public class PaymentEntity {
    @Id private UUID id; private UUID orderId; private BigDecimal amount;
    @Enumerated(EnumType.STRING) private Payment.Status status;
    private Instant processedAt;
    public UUID getId(){return id;} public void setId(UUID v){id=v;}
    public UUID getOrderId(){return orderId;} public void setOrderId(UUID v){orderId=v;}
    public BigDecimal getAmount(){return amount;} public void setAmount(BigDecimal v){amount=v;}
    public Payment.Status getStatus(){return status;} public void setStatus(Payment.Status v){status=v;}
    public Instant getProcessedAt(){return processedAt;} public void setProcessedAt(Instant v){processedAt=v;}
}
