package com.company.shipping.domain.repository;

import com.company.shipping.domain.model.Shipment;

public interface IShippingRepository {
    Shipment save(Shipment shipment);
}
