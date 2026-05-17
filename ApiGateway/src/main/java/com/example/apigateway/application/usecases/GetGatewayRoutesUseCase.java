package com.example.apigateway.application.usecases;

import com.example.apigateway.application.dto.response.GatewayRouteResponse;
import com.example.apigateway.application.usecases.GetGatewayRoutesService;
import com.example.apigateway.domain.interfaces.RouteCatalogRepository;
import com.example.apigateway.domain.services.RouteCatalogDomainService;
import java.util.List;

public class GetGatewayRoutesUseCase implements GetGatewayRoutesService {

    private final RouteCatalogRepository routeCatalogRepository;
    private final RouteCatalogDomainService routeCatalogDomainService;

    public GetGatewayRoutesUseCase(
            RouteCatalogRepository routeCatalogRepository,
            RouteCatalogDomainService routeCatalogDomainService
    ) {
        this.routeCatalogRepository = routeCatalogRepository;
        this.routeCatalogDomainService = routeCatalogDomainService;
    }

    public List<GatewayRouteResponse> execute() {
        return routeCatalogDomainService.orderRoutes(routeCatalogRepository.findAll()).stream()
                .map(route -> new GatewayRouteResponse(
                        route.routeId(),
                        route.uri(),
                        route.path(),
                        route.routeType().name()
                ))
                .toList();
    }
}
