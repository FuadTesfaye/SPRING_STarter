package com.example.shipmentservice.presentation.controllers;

import com.example.shipmentservice.application.dto.response.ShipmentResponse;
import com.example.shipmentservice.application.usecases.CreateShipmentService;
import com.example.shipmentservice.presentation.controllers.request.ShipmentRequest;
import com.example.shipmentservice.presentation.mapper.ShipmentPresentationMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shipping")
public class ShipmentController {

    private final CreateShipmentService createShipmentUseCase;
    private final ShipmentPresentationMapper shipmentPresentationMapper;

    public ShipmentController(CreateShipmentService createShipmentUseCase) {
        this.createShipmentUseCase = createShipmentUseCase;
        this.shipmentPresentationMapper = new ShipmentPresentationMapper();
    }

    @PostMapping
    public ShipmentResponse createShipment(@RequestBody ShipmentRequest request) {
        return shipmentPresentationMapper.toPresentation(
                createShipmentUseCase.execute(shipmentPresentationMapper.toApplication(request))
        );
    }
}
