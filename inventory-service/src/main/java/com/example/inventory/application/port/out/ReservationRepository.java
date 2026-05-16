package com.example.inventory.application.port.out;

import com.example.inventory.domain.model.Reservation;

import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    Optional<Reservation> findByOrderId(Long orderId);

    Optional<Reservation> findById(Long id);
}
