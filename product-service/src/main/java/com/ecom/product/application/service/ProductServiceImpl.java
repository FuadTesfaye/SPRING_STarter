package com.ecom.product.application.service;

import com.ecom.product.application.dto.ProductRequest;
import com.ecom.product.application.dto.ProductResponse;
import com.ecom.product.domain.model.Product;
import com.ecom.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl {
    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    public ProductResponse createProduct(ProductRequest request) {
        Product product = new Product(
                UUID.randomUUID(),
                request.name(),
                request.description(),
                request.price(),
                request.category(),
                request.stockQuantity()
        );
        Product saved = repository.save(product);
        return mapToResponse(saved);
    }

    public List<ProductResponse> getAllProducts() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void deleteProduct(UUID id) {
        repository.deleteById(id);
    }

    private ProductResponse mapToResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory(),
                product.getStockQuantity()
        );
    }
}
