package com.company.shipping.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Shipment {
    private Long id;
    private Long orderId;
    private String trackingNumber;
    private String status;

    public Shipment(Long orderId, String trackingNumber) {
        this.orderId = orderId;
        this.trackingNumber = trackingNumber;
        this.status = "SHIPPED";
    }
}
