package com.company.shipping.application.usecase;

import com.company.shipping.domain.model.Shipment;
import com.company.shipping.domain.repository.IShippingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateShipmentUseCase {
    private final IShippingRepository shippingRepository;

    public Shipment execute(Long orderId) {
        String trackingNumber = "TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Shipment shipment = new Shipment(orderId, trackingNumber);
        return shippingRepository.save(shipment);
    }
}
