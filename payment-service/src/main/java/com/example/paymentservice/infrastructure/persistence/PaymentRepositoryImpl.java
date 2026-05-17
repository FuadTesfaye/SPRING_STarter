package com.example.paymentservice.infrastructure.persistence;


import com.example.paymentservice.domain.model.Payment;
import com.example.paymentservice.domain.repository.PaymentRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private final SpringDataPaymentRepository jpaRepository;

    public PaymentRepositoryImpl(SpringDataPaymentRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Payment save(Payment payment) {
        PaymentEntity entity = new PaymentEntity();
        entity.setId(payment.getId());
        entity.setOrderId(payment.getOrderId());
        entity.setUserId(payment.getUserId());
        entity.setAmount(payment.getAmount());
        entity.setStatus(payment.getStatus());

        PaymentEntity savedEntity = jpaRepository.save(entity);

        // Convert back to domain model
        Payment savedPayment = new Payment();
        savedPayment.setId(savedEntity.getId());
        savedPayment.setOrderId(savedEntity.getOrderId());
        savedPayment.setUserId(savedEntity.getUserId());
        savedPayment.setAmount(savedEntity.getAmount());
        savedPayment.setStatus(savedEntity.getStatus());

        return savedPayment;
    }

    @Override
    public boolean existsByOrderId(UUID orderId) {
        return false;
    }

    @Override
    public Optional<Payment> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(entity -> {
                    Payment payment = new Payment();
                    payment.setId(entity.getId());
                    payment.setOrderId(entity.getOrderId());
                    payment.setUserId(entity.getUserId());
                    payment.setAmount(entity.getAmount());
                    payment.setStatus(entity.getStatus());
                    return payment;
                });
    }
}