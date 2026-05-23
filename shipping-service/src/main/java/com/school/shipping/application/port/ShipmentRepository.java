package com.school.shipping.application.port;

import com.school.shipping.domain.entity.Shipment;

public interface ShipmentRepository {
    Shipment save(Shipment shipment);
}
