package com.example.apigateway.infrastructure.config;

import com.example.apigateway.application.usecases.GetGatewayRoutesService;
import com.example.apigateway.application.usecases.GetGatewayRoutesUseCase;
import com.example.apigateway.domain.entities.GatewayRoute;
import com.example.apigateway.domain.interfaces.RouteCatalogRepository;
import com.example.apigateway.domain.services.RouteCatalogDomainService;
import com.example.apigateway.infrastructure.persistence.adapter.RouteCatalogPersistenceMapper;
import com.example.apigateway.infrastructure.persistence.adapter.RouteCatalogRepositoryAdapter;
import com.example.apigateway.infrastructure.persistence.repository.InMemoryRouteCatalogStore;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {

    @Bean
    public InMemoryRouteCatalogStore inMemoryRouteCatalogStore() {
        return new InMemoryRouteCatalogStore();
    }

    @Bean
    public RouteCatalogPersistenceMapper routeCatalogPersistenceMapper() {
        return new RouteCatalogPersistenceMapper();
    }

    @Bean
    public RouteCatalogRepository routeCatalogRepository(
            InMemoryRouteCatalogStore inMemoryRouteCatalogStore,
            RouteCatalogPersistenceMapper routeCatalogPersistenceMapper
    ) {
        return new RouteCatalogRepositoryAdapter(inMemoryRouteCatalogStore, routeCatalogPersistenceMapper);
    }

    @Bean
    public RouteCatalogDomainService routeCatalogDomainService() {
        return new RouteCatalogDomainService();
    }

    @Bean
    public GetGatewayRoutesService getGatewayRoutesUseCase(
            RouteCatalogRepository routeCatalogRepository,
            RouteCatalogDomainService routeCatalogDomainService
    ) {
        return new GetGatewayRoutesUseCase(routeCatalogRepository, routeCatalogDomainService);
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder, RouteCatalogRepository routeCatalogRepository) {
        RouteLocatorBuilder.Builder routes = builder.routes();
        for (GatewayRoute route : routeCatalogRepository.findAll()) {
            routes.route(route.routeId(), predicate -> predicate.path(route.path()).uri(route.uri()));
        }
        return routes.build();
    }
}
