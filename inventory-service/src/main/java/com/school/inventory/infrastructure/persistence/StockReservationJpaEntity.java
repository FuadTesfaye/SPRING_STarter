package com.school.inventory.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_reservations")
@Getter
@Setter
public class StockReservationJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private String studentId;
    private String feeType;
    private String status;
    private LocalDateTime reservedAt;
}
