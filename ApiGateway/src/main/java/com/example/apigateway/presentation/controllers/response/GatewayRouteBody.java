package com.example.apigateway.presentation.controllers.response;

public record GatewayRouteBody(String routeId, String uri, String path, String routeType) {
}
