package com.example.apigateway.infrastructure.persistence.repository;

import com.example.apigateway.infrastructure.persistence.entity.GatewayRouteRecord;
import java.util.List;

public class InMemoryRouteCatalogStore {

    public List<GatewayRouteRecord> findAll() {
        return List.of(
                new GatewayRouteRecord("auth-service", "http://localhost:8086", "/api/auth/**", "HTTP"),
                new GatewayRouteRecord("inventory-service", "http://localhost:8083", "/api/inventory/**", "HTTP"),
                new GatewayRouteRecord("notification-service", "http://localhost:8085", "/api/notifications/**", "HTTP"),
                new GatewayRouteRecord("order-service", "http://localhost:8081", "/api/orders/**", "HTTP"),
                new GatewayRouteRecord("payment-service", "http://localhost:8082", "/api/payments/**", "HTTP"),
                new GatewayRouteRecord("shipping-service", "http://localhost:8084", "/api/shipping/**", "HTTP")
        );
    }
}
