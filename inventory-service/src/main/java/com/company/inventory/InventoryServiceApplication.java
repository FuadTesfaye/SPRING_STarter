package com.company.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.company.inventory.domain.model.ProductStock;
import com.company.inventory.domain.repository.IInventoryRepository;

@SpringBootApplication
public class InventoryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner seedInventory(IInventoryRepository repository) {
        return args -> {
            repository.save(new ProductStock("1", 50));
            repository.save(new ProductStock("2", 100));
            repository.save(new ProductStock("3", 25));
            System.out.println("Inventory data seeded successfully!");
        };
    }
}
