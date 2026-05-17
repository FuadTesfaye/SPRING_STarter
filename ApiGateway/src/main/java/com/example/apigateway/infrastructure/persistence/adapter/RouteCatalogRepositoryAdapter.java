package com.example.apigateway.infrastructure.persistence.adapter;

import com.example.apigateway.domain.entities.GatewayRoute;
import com.example.apigateway.domain.interfaces.RouteCatalogRepository;
import com.example.apigateway.infrastructure.persistence.repository.InMemoryRouteCatalogStore;
import java.util.List;

public class RouteCatalogRepositoryAdapter implements RouteCatalogRepository {

    private final InMemoryRouteCatalogStore inMemoryRouteCatalogStore;
    private final RouteCatalogPersistenceMapper routeCatalogPersistenceMapper;

    public RouteCatalogRepositoryAdapter(
            InMemoryRouteCatalogStore inMemoryRouteCatalogStore,
            RouteCatalogPersistenceMapper routeCatalogPersistenceMapper
    ) {
        this.inMemoryRouteCatalogStore = inMemoryRouteCatalogStore;
        this.routeCatalogPersistenceMapper = routeCatalogPersistenceMapper;
    }

    @Override
    public List<GatewayRoute> findAll() {
        return inMemoryRouteCatalogStore.findAll().stream().map(routeCatalogPersistenceMapper::toDomain).toList();
    }
}
