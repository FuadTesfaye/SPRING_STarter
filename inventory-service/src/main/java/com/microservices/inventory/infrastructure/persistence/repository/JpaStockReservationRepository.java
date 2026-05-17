package com.microservices.inventory.infrastructure.persistence.repository;

import com.microservices.inventory.infrastructure.persistence.entity.StockReservationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JpaStockReservationRepository extends JpaRepository<StockReservationJpaEntity, String> {
    List<StockReservationJpaEntity> findByOrderId(String orderId);
}
