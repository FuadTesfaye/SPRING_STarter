package com.ecommerce.payment.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String orderId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    private String failureReason;

    @Column(nullable = false)
    private LocalDateTime processedAt;

    // Domain behavior: mock payment logic
    public boolean process() {
        // 80% success rate for simulation
        boolean success = Math.random() > 0.2;
        this.status = success ? PaymentStatus.COMPLETED : PaymentStatus.FAILED;
        if (!success) this.failureReason = "Insufficient funds (simulated)";
        return success;
    }
}
