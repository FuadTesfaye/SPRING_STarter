package com.microservices.payment.infrastructure.persistence.repository;

import com.microservices.payment.domain.model.Payment;
import com.microservices.payment.domain.repository.PaymentRepository;
import com.microservices.payment.infrastructure.persistence.entity.PaymentJpaEntity;
import com.microservices.payment.infrastructure.persistence.mapper.PaymentPersistenceMapper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PaymentRepositoryImpl implements PaymentRepository {
    
    private final JpaPaymentRepository jpaPaymentRepository;
    
    public PaymentRepositoryImpl(JpaPaymentRepository jpaPaymentRepository) {
        this.jpaPaymentRepository = jpaPaymentRepository;
    }
    
    @Override
    public Payment save(Payment payment) {
        PaymentJpaEntity entity = PaymentPersistenceMapper.toJpaEntity(payment);
        PaymentJpaEntity saved = jpaPaymentRepository.save(entity);
        return PaymentPersistenceMapper.toDomain(saved);
    }
    
    @Override
    public Optional<Payment> findById(String id) {
        return jpaPaymentRepository.findById(id)
            .map(PaymentPersistenceMapper::toDomain);
    }
    
    @Override
    public List<Payment> findByOrderId(String orderId) {
        return jpaPaymentRepository.findByOrderId(orderId).stream()
            .map(PaymentPersistenceMapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Payment> findAll() {
        return jpaPaymentRepository.findAll().stream()
            .map(PaymentPersistenceMapper::toDomain)
            .collect(Collectors.toList());
    }
}
