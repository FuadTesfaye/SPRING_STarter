package com.example.shippingservice.infrastructure.persistence;
import jakarta.persistence.*;
import java.time.Instant; import java.util.UUID;
@Entity @Table(name="shipments")
public class ShipmentEntity {
    @Id private UUID id;
    @Column(unique=true) private UUID orderId;
    private String status;
    private Instant createdAt;
    public UUID getId(){return id;} public void setId(UUID v){id=v;}
    public UUID getOrderId(){return orderId;} public void setOrderId(UUID v){orderId=v;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;}
    public Instant getCreatedAt(){return createdAt;} public void setCreatedAt(Instant v){createdAt=v;}
}
