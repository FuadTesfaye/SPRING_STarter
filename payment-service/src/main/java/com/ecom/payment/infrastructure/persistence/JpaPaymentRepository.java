package com.ecom.payment.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface JpaPaymentRepository extends JpaRepository<PaymentEntity, UUID> {
    java.util.Optional<PaymentEntity> findByOrderId(UUID orderId);
}
