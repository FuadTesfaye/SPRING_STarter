package com.company.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.company.product.domain.model.Product;
import com.company.product.application.usecase.ProductUseCase;

@SpringBootApplication
public class ProductServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner seedData(ProductUseCase productUseCase) {
        return args -> {
            productUseCase.createProduct(new Product("1", "EliteBook Pro", "Ultra-thin high-end professional laptop.", 1299.99, "/images/laptop.png", "Laptops"));
            productUseCase.createProduct(new Product("2", "SonicNoise Z", "Professional noise-canceling over-ear headphones.", 299.99, "/images/headphones.png", "Audio"));
            productUseCase.createProduct(new Product("3", "Chronos Silver", "Luxury minimalist wrist watch with a leather strap.", 450.00, "/images/watch.png", "Watches"));
            System.out.println("Product data seeded successfully!");
        };
    }
}
