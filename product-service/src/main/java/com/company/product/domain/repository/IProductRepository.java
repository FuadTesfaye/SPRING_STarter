package com.company.product.domain.repository;

import com.company.product.domain.model.Product;
import java.util.List;
import java.util.Optional;

public interface IProductRepository {
    List<Product> findAll();
    Optional<Product> findById(String id);
    Product save(Product product);
    void deleteById(String id);
}
