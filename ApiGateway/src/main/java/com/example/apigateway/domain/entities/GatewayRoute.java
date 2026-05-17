package com.example.apigateway.domain.entities;

import com.example.apigateway.domain.enums.RouteType;

public class GatewayRoute {

    private final String routeId;
    private final String uri;
    private final String path;
    private final RouteType routeType;

    public GatewayRoute(String routeId, String uri, String path, RouteType routeType) {
        this.routeId = routeId;
        this.uri = uri;
        this.path = path;
        this.routeType = routeType;
    }

    public String routeId() {
        return routeId;
    }

    public String uri() {
        return uri;
    }

    public String path() {
        return path;
    }

    public RouteType routeType() {
        return routeType;
    }
}
