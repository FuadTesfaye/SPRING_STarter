package com.example.apigateway.presentation.controllers;

import com.example.apigateway.application.usecases.GetGatewayRoutesService;
import com.example.apigateway.presentation.controllers.response.GatewayRouteBody;
import com.example.apigateway.presentation.mapper.GatewayRoutePresentationMapper;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gateway/routes")
public class GatewayRouteController {

    private final GetGatewayRoutesService getGatewayRoutesUseCase;
    private final GatewayRoutePresentationMapper gatewayRoutePresentationMapper;

    public GatewayRouteController(GetGatewayRoutesService getGatewayRoutesUseCase) {
        this.getGatewayRoutesUseCase = getGatewayRoutesUseCase;
        this.gatewayRoutePresentationMapper = new GatewayRoutePresentationMapper();
    }

    @GetMapping
    public List<GatewayRouteBody> getRoutes() {
        return getGatewayRoutesUseCase.execute().stream().map(gatewayRoutePresentationMapper::toPresentation).toList();
    }
}
