package com.example.apigateway.application.usecases;

import com.example.apigateway.application.dto.response.GatewayRouteResponse;
import java.util.List;

public interface GetGatewayRoutesService {

    List<GatewayRouteResponse> execute();
}
