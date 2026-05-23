package com.assignment.payment.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JpaPaymentRepository extends JpaRepository<PaymentEntity, String> {
    List<PaymentEntity> findByOrderId(String orderId);
}
