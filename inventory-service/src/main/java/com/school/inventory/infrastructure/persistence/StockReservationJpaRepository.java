package com.school.inventory.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockReservationJpaRepository extends JpaRepository<StockReservationJpaEntity, Long> {
}
