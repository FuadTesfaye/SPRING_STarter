package com.microservices.inventory.application.mapper;

import com.microservices.inventory.application.dto.StockResponse;
import com.microservices.inventory.domain.model.StockReservation;

import java.util.List;
import java.util.stream.Collectors;

public final class StockMapper {
    
    private StockMapper() {}
    
    public static StockResponse toResponse(StockReservation reservation) {
        if (reservation == null) return null;
        StockResponse response = new StockResponse();
        response.setReservationId(reservation.getId());
        response.setOrderId(reservation.getOrderId());
        response.setProductId(reservation.getProductId());
        response.setProductName(reservation.getProductName());
        response.setQuantity(reservation.getQuantity());
        response.setStatus(reservation.getStatus().name());
        response.setReason(reservation.getReason());
        response.setProcessedAt(reservation.getProcessedAt());
        return response;
    }
    
    public static List<StockResponse> toResponseList(List<StockReservation> reservations) {
        return reservations.stream()
            .map(StockMapper::toResponse)
            .collect(Collectors.toList());
    }
}
