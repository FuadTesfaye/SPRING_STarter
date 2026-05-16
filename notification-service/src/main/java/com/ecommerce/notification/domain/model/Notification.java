package com.ecommerce.notification.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String eventType;

    @Column(nullable = false, length = 2000)
    private String message;

    @Column(nullable = false)
    private String referenceId;   // orderId, userId, shipmentId etc.

    @Column(nullable = false)
    private LocalDateTime receivedAt;
}
