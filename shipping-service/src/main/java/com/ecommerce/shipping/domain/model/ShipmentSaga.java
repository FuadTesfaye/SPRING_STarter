package com.ecommerce.shipping.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Saga state machine: waits for BOTH payment.completed AND stock.reserved
 * before creating the shipment. This is the Saga Pattern for distributed transactions.
 */
@Entity
@Table(name = "shipment_sagas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ShipmentSaga {

    @Id
    @Column(nullable = false, unique = true)
    private String orderId;

    private boolean paymentCompleted;
    private boolean stockReserved;
    private String shippingAddress;
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SagaStatus status;

    public enum SagaStatus {
        WAITING, READY, COMPLETED, FAILED
    }

    public boolean isReadyToShip() {
        return paymentCompleted && stockReserved;
    }

    public void markPaymentCompleted() {
        this.paymentCompleted = true;
        this.updatedAt = LocalDateTime.now();
        if (isReadyToShip()) this.status = SagaStatus.READY;
    }

    public void markStockReserved(String address) {
        this.stockReserved = true;
        this.shippingAddress = address;
        this.updatedAt = LocalDateTime.now();
        if (isReadyToShip()) this.status = SagaStatus.READY;
    }
}
