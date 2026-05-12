package com.microservices.paymentservice.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository
        extends JpaRepository<PaymentEntity, Long> {
}