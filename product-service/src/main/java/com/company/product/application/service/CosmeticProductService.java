package com.company.product.application.service;

import com.company.product.application.dto.CosmeticProductFilterRequest;
import com.company.product.application.dto.CosmeticProductResponse;
import com.company.product.application.dto.ShadeResponse;
import com.company.product.domain.model.CosmeticProduct;
import com.company.product.domain.repository.CosmeticProductRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CosmeticProductService {

    private final CosmeticProductRepository repository;

    public CosmeticProductService(CosmeticProductRepository repository) {
        this.repository = repository;
    }

    public List<CosmeticProductResponse> filterProducts(CosmeticProductFilterRequest request) {
        // Implement complex specification logic here. For now, returning all.
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ShadeResponse> getProductShades(String productId) {
        return repository.findByProductId(productId)
                .map(product -> product.getShades().stream()
                        .map(shade -> new ShadeResponse(shade, shade, "#000000", true))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    public List<CosmeticProductResponse> getSimilarProducts(String productId) {
        return repository.findTop10ByOrderByAverageRatingDesc().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<CosmeticProductResponse> getRoutinePairings(String productId) {
        return repository.findAll().stream()
                .limit(5)
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<CosmeticProductResponse> getNewArrivals() {
        return repository.findAll().stream()
                .limit(10)
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<CosmeticProductResponse> getBestSellers() {
        return repository.findTop50ByOrderBySalesCountDesc().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<CosmeticProductResponse> getCleanBeauty() {
        return repository.findByCrueltyFreeTrueAndVeganTrue().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<CosmeticProductResponse> getByIngredient(String ingredient) {
        // Assume repository has a method to search within collection
        return repository.findAll().stream()
                .filter(p -> p.getKeyIngredients() != null && p.getKeyIngredients().contains(ingredient))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<String> getAllBrands() {
        return repository.findAll().stream()
                .map(CosmeticProduct::getBrand)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    private CosmeticProductResponse mapToResponse(CosmeticProduct product) {
        CosmeticProductResponse response = new CosmeticProductResponse();
        response.setId(product.getId());
        response.setProductId(product.getProductId());
        response.setBrand(product.getBrand());
        response.setPrimaryCategory(product.getPrimaryCategory());
        response.setAverageRating(product.getAverageRating());
        return response;
    }
}
