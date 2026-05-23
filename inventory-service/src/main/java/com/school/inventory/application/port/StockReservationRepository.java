package com.school.inventory.application.port;

import com.school.inventory.domain.entity.StockReservation;

public interface StockReservationRepository {
    StockReservation save(StockReservation reservation);
}
