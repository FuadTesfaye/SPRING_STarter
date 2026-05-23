package com.example.shippingservice.domain.model;
import java.time.Instant; import java.util.UUID;
public class Shipment {
    private UUID id; private UUID orderId; private String status; private Instant createdAt;
    public Shipment() {}
    public Shipment(UUID id, UUID orderId, String status, Instant t) {
        this.id=id; this.orderId=orderId; this.status=status; this.createdAt=t;
    }
    public UUID getId(){return id;} public void setId(UUID v){id=v;}
    public UUID getOrderId(){return orderId;} public void setOrderId(UUID v){orderId=v;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;}
    public Instant getCreatedAt(){return createdAt;} public void setCreatedAt(Instant v){createdAt=v;}
}
