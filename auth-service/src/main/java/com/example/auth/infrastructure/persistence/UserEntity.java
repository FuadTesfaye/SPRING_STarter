package com.example.auth.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;


@Entity @Table(name="users") @Data @NoArgsConstructor
@AllArgsConstructor
@Getter @Setter

public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String username;
    private String password;

}
