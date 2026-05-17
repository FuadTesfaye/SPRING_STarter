package com.microservices.inventory.domain.repository;

import com.microservices.inventory.domain.model.StockReservation;
import java.util.List;
import java.util.Optional;

public interface StockReservationRepository {
    StockReservation save(StockReservation reservation);
    Optional<StockReservation> findById(String id);
    List<StockReservation> findByOrderId(String orderId);
    List<StockReservation> findAll();
}
