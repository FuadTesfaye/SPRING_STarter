package com.school.order.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class OrderJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentId;
    private String studentName;
    private String feeType;

    @Column(precision = 10, scale = 2)
    private BigDecimal amount;

    private String status;
    private LocalDateTime createdAt;
}
