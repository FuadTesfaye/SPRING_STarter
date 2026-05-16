package com.ecommerce.inventory.infrastructure.persistence;

import com.ecommerce.inventory.domain.model.Inventory;
import com.ecommerce.inventory.domain.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaInventoryRepository implements InventoryRepository {
    private final SpringDataInventoryRepository repo;

    @Override public Inventory save(Inventory i) { return repo.save(i); }
    @Override public Optional<Inventory> findByProductId(String pid) { return repo.findByProductId(pid); }
    @Override public List<Inventory> findAll() { return repo.findAll(); }
}
