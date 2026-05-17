package com.example.paymentservice.infrastructure.persistence.repository;

import com.example.paymentservice.infrastructure.persistence.entity.PaymentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPaymentRepository extends JpaRepository<PaymentJpaEntity, String> {
}
