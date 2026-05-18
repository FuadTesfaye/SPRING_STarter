package com.company.product.domain.repository;

import com.company.product.domain.model.CosmeticProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CosmeticProductRepository extends JpaRepository<CosmeticProduct, String>, JpaSpecificationExecutor<CosmeticProduct> {
    Optional<CosmeticProduct> findByProductId(String productId);
    List<CosmeticProduct> findTop50ByOrderBySalesCountDesc();
    List<CosmeticProduct> findTop10ByOrderByAverageRatingDesc();
    List<CosmeticProduct> findByCrueltyFreeTrueAndVeganTrue();
}
