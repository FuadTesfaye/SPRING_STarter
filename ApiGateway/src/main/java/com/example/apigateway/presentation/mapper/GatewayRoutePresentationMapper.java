package com.example.apigateway.presentation.mapper;

import com.example.apigateway.application.dto.response.GatewayRouteResponse;
import com.example.apigateway.presentation.controllers.response.GatewayRouteBody;

public class GatewayRoutePresentationMapper {

    public GatewayRouteBody toPresentation(GatewayRouteResponse response) {
        return new GatewayRouteBody(response.routeId(), response.uri(), response.path(), response.routeType());
    }
}
