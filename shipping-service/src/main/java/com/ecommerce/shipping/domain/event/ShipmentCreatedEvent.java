package com.ecommerce.shipping.domain.event;

import lombok.*;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ShipmentCreatedEvent {
    private String shipmentId;
    private String orderId;
    private String shippingAddress;
    private String trackingNumber;
    private LocalDateTime timestamp;
}
