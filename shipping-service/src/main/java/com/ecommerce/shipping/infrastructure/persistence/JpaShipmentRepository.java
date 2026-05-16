package com.ecommerce.shipping.infrastructure.persistence;

import com.ecommerce.shipping.domain.model.Shipment;
import com.ecommerce.shipping.domain.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaShipmentRepository implements ShipmentRepository {
    private final SpringDataShipmentRepository repo;

    @Override public Shipment save(Shipment s) { return repo.save(s); }
    @Override public Optional<Shipment> findByOrderId(String oid) { return repo.findByOrderId(oid); }
    @Override public List<Shipment> findAll() { return repo.findAll(); }
}
