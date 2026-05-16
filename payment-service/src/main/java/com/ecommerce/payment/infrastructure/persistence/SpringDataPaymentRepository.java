package com.ecommerce.payment.infrastructure.persistence;

import com.ecommerce.payment.domain.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SpringDataPaymentRepository extends JpaRepository<Payment, String> {
    Optional<Payment> findByOrderId(String orderId);
}
