package com.school.payment.application.port;

import com.school.payment.domain.entity.Payment;
import java.util.Optional;

public interface PaymentRepository {
    Payment save(Payment payment);
    Optional<Payment> findById(Long id);
}
