package com.ecommerce.notification.domain.event;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ShipmentCreatedEvent {
    private String shipmentId;
private String orderId;
private String shippingAddress;
private String trackingNumber;
private java.time.LocalDateTime timestamp;
}
