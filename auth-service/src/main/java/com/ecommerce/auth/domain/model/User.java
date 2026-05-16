package com.ecommerce.auth.domain.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Domain Entity - represents a user in the system.
 * Note: @Entity is used here pragmatically; business logic remains framework-free.
 */
@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    // Domain behavior
    public boolean isAdmin() {
        return "ADMIN".equals(this.role);
    }
}
