package com.school.payment.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
public class PaymentJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private String studentId;

    @Column(precision = 10, scale = 2)
    private BigDecimal amount;

    private String status;
    private String reference;
    private LocalDateTime processedAt;
}
