package com.example.apigateway.infrastructure.persistence.adapter;

import com.example.apigateway.domain.entities.GatewayRoute;
import com.example.apigateway.domain.enums.RouteType;
import com.example.apigateway.infrastructure.persistence.entity.GatewayRouteRecord;

public class RouteCatalogPersistenceMapper {

    public GatewayRoute toDomain(GatewayRouteRecord record) {
        return new GatewayRoute(record.routeId(), record.uri(), record.path(), RouteType.valueOf(record.routeType()));
    }
}
