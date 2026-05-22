package com.ecommerce.shipping.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private boolean paymentCompleted;
    private boolean stockReserved;
    private boolean shipped;
}
