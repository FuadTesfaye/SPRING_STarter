package com.school.shipping.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "shipments")
@Getter
@Setter
public class ShipmentJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private String studentId;
    private String trackingNumber;
    private String status;
    private LocalDateTime createdAt;
}
