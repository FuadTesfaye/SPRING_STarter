package com.microservices.inventory.application.service;

import com.microservices.inventory.application.dto.StockResponse;
import com.microservices.inventory.application.mapper.StockMapper;
import com.microservices.inventory.domain.model.StockReservation;
import com.microservices.inventory.domain.service.InventoryDomainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class InventoryApplicationService {
    
    private static final Logger log = LoggerFactory.getLogger(InventoryApplicationService.class);
    
    private final InventoryDomainService domainService;
    
    public InventoryApplicationService(InventoryDomainService domainService) {
        this.domainService = domainService;
    }
    
    public StockResponse checkAndReserve(String orderId, String productId,
                                          String productName, int quantity) {
        log.info("Processing stock check for order: {}, product: {}", orderId, productId);
        StockReservation reservation = domainService.checkAndReserve(orderId, productId, productName, quantity);
        return StockMapper.toResponse(reservation);
    }
    
    public StockResponse getReservation(String id) {
        StockReservation reservation = domainService.findById(id);
        return StockMapper.toResponse(reservation);
    }
    
    public List<StockResponse> getByOrderId(String orderId) {
        List<StockReservation> reservations = domainService.findByOrderId(orderId);
        return StockMapper.toResponseList(reservations);
    }
    
    public List<StockResponse> getAll() {
        List<StockReservation> reservations = domainService.findAll();
        return StockMapper.toResponseList(reservations);
    }
}
