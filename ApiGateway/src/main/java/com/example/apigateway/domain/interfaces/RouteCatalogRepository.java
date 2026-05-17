package com.example.apigateway.domain.interfaces;

import com.example.apigateway.domain.entities.GatewayRoute;
import java.util.List;

public interface RouteCatalogRepository {

    List<GatewayRoute> findAll();
}
