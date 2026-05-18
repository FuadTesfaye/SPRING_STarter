package com.app.payment.domain;

import java.util.UUID;

public interface PaymentRepository {
    Payment save(Payment payment);
}
