package com.example.shippingservice.domain.event;
import java.time.Instant; import java.util.UUID;
public class ShipmentCreatedEvent {
    private UUID shipmentId; private UUID orderId; private Instant occurredAt;
    public ShipmentCreatedEvent() {}
    public ShipmentCreatedEvent(UUID s, UUID o, Instant t) { shipmentId=s; orderId=o; occurredAt=t; }
    public UUID getShipmentId(){return shipmentId;} public void setShipmentId(UUID v){shipmentId=v;}
    public UUID getOrderId(){return orderId;} public void setOrderId(UUID v){orderId=v;}
    public Instant getOccurredAt(){return occurredAt;} public void setOccurredAt(Instant v){occurredAt=v;}
}
