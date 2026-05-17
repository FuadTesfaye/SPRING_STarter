package com.example.apigateway.infrastructure.persistence.entity;

public record GatewayRouteRecord(String routeId, String uri, String path, String routeType) {
}
