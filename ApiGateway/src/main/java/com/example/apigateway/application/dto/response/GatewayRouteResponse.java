package com.example.apigateway.application.dto.response;

public record GatewayRouteResponse(String routeId, String uri, String path, String routeType) {
}
