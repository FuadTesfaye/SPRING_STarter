package com.microservices.payment.infrastructure.persistence.repository;

import com.microservices.payment.infrastructure.persistence.entity.PaymentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JpaPaymentRepository extends JpaRepository<PaymentJpaEntity, String> {
    List<PaymentJpaEntity> findByOrderId(String orderId);
}
