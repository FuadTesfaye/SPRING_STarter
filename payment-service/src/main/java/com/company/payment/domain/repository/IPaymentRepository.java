package com.company.payment.domain.repository;

import com.company.payment.domain.model.Payment;

public interface IPaymentRepository {
    Payment save(Payment payment);
}
