package com.ecommerce.shipping.infrastructure.persistence;

import com.ecommerce.shipping.domain.model.ShipmentSaga;
import com.ecommerce.shipping.domain.repository.ShipmentSagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaShipmentSagaRepository implements ShipmentSagaRepository {
    private final SpringDataShipmentSagaRepository repo;

    @Override public ShipmentSaga save(ShipmentSaga s) { return repo.save(s); }
    @Override public Optional<ShipmentSaga> findByOrderId(String oid) { return repo.findByOrderId(oid); }
}
