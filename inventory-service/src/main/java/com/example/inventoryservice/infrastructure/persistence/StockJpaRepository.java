package com.example.inventoryservice.infrastructure.persistence;
import org.springframework.data.jpa.repository.JpaRepository;
public interface StockJpaRepository extends JpaRepository<StockEntity, String> {}
