package com.example.paymentservice.infrastructure.persistence.adapter;

import com.example.paymentservice.domain.entities.Payment;
import com.example.paymentservice.domain.interfaces.PaymentRepository;
import com.example.paymentservice.infrastructure.persistence.repository.SpringDataPaymentRepository;

public class PaymentPersistenceAdapter implements PaymentRepository {

    private final SpringDataPaymentRepository springDataPaymentRepository;
    private final PaymentPersistenceMapper paymentPersistenceMapper;

    public PaymentPersistenceAdapter(
            SpringDataPaymentRepository springDataPaymentRepository,
            PaymentPersistenceMapper paymentPersistenceMapper
    ) {
        this.springDataPaymentRepository = springDataPaymentRepository;
        this.paymentPersistenceMapper = paymentPersistenceMapper;
    }

    @Override
    public Payment save(Payment payment) {
        return paymentPersistenceMapper.toDomain(
                springDataPaymentRepository.save(paymentPersistenceMapper.toEntity(payment))
        );
    }
}
