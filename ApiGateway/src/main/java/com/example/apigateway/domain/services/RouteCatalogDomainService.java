package com.example.apigateway.domain.services;

import com.example.apigateway.domain.entities.GatewayRoute;
import java.util.Comparator;
import java.util.List;

public class RouteCatalogDomainService {

    public List<GatewayRoute> orderRoutes(List<GatewayRoute> routes) {
        return routes.stream().sorted(Comparator.comparing(GatewayRoute::routeId)).toList();
    }
}
