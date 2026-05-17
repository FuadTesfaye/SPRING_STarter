package com.example.inventservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.inventservice.infrastructure.persistence")
public class InventServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventServiceApplication.class, args);
    }
}