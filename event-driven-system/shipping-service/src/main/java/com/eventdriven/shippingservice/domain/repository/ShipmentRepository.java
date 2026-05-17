package com.eventdriven.shippingservice.domain.repository;

import com.eventdriven.shippingservice.domain.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}