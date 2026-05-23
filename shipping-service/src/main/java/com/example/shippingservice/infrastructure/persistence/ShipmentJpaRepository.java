package com.example.shippingservice.infrastructure.persistence;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional; import java.util.UUID;
public interface ShipmentJpaRepository extends JpaRepository<ShipmentEntity, UUID> {
    Optional<ShipmentEntity> findByOrderId(UUID orderId);
}
