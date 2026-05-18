package com.assignment.shipping.domain.repository;


import com.assignment.shipping.domain.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}