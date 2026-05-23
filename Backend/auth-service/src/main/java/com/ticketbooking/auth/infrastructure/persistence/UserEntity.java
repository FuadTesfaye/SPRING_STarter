package com.ticketbooking.auth.infrastructure.persistence;

import com.ticketbooking.auth.domain.model.User;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(columnDefinition = "VARCHAR(36)")
    public String id;

    @Column(unique = true, nullable = false)
    public String email;

    @Column(nullable = false)
    public String passwordHash;

    @Column(nullable = false)
    public String fullName;

    @Column(nullable = false)
    public String role;

    public Instant createdAt;

    @Column(nullable = false)
    public boolean verified = false;

    public String verificationToken;

    @Column(nullable = false)
    public boolean banned = false;

    public static UserEntity fromDomain(User user) {
        UserEntity e = new UserEntity();
        e.id = user.getId().toString();
        e.email = user.getEmail();
        e.passwordHash = user.getPasswordHash();
        e.fullName = user.getFullName();
        e.role = user.getRole();
        e.createdAt = user.getCreatedAt();
        e.verified = user.isVerified();
        e.verificationToken = user.getVerificationToken();
        e.banned = user.isBanned();
        return e;
    }

    public User toDomain() {
        return new User(UUID.fromString(id), email, passwordHash, fullName, role,
                createdAt, verified, verificationToken, banned);
    }
}
