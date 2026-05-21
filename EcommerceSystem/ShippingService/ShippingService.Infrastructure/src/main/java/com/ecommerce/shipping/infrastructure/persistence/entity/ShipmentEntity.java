package com.ecommerce.shipping.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentEntity {
    @Id
    private String id;
    private String orderId;
    private String trackingNumber;
    private String status;
    private LocalDateTime estimatedDelivery;
}
