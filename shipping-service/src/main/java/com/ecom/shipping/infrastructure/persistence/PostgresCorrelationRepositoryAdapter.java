package com.ecom.shipping.infrastructure.persistence;

import com.ecom.shipping.domain.model.ShippingCorrelation;
import com.ecom.shipping.domain.repository.ShippingCorrelationRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.UUID;

@Component
public class PostgresCorrelationRepositoryAdapter implements ShippingCorrelationRepository {
    private final JpaCorrelationRepository repository;

    public PostgresCorrelationRepositoryAdapter(JpaCorrelationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<ShippingCorrelation> findByOrderId(UUID orderId) {
        return repository.findById(orderId)
                .map(entity -> new ShippingCorrelation(entity.getOrderId(), entity.isPaymentCompleted(), entity.isStockReserved()));
    }

    @Override
    public ShippingCorrelation save(ShippingCorrelation correlation) {
        ShippingCorrelationEntity entity = new ShippingCorrelationEntity(
                correlation.getOrderId(),
                correlation.isPaymentCompleted(),
                correlation.isStockReserved()
        );
        ShippingCorrelationEntity saved = repository.save(entity);
        return new ShippingCorrelation(saved.getOrderId(), saved.isPaymentCompleted(), saved.isStockReserved());
    }
}
