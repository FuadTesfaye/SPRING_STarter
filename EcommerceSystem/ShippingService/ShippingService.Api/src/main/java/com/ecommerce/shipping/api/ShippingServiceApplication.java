package com.ecommerce.shipping.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.ecommerce.shipping")
@EnableJpaRepositories(basePackages = "com.ecommerce.shipping.infrastructure.persistence.repository")
@EntityScan(basePackages = "com.ecommerce.shipping.infrastructure.persistence.entity")
public class ShippingServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShippingServiceApplication.class, args);
    }
}
