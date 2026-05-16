package com.ecommerce.auth.infrastructure.persistence;

import com.ecommerce.auth.domain.model.User;
import com.ecommerce.auth.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

/**
 * Infrastructure adapter: implements Domain UserRepository using Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class JpaUserRepository implements UserRepository {

    private final SpringDataUserRepository repo;

    @Override public User save(User user) { return repo.save(user); }
    @Override public Optional<User> findByEmail(String e) { return repo.findByEmail(e); }
    @Override public Optional<User> findByUsername(String u) { return repo.findByUsername(u); }
    @Override public boolean existsByEmail(String e) { return repo.existsByEmail(e); }
    @Override public boolean existsByUsername(String u) { return repo.existsByUsername(u); }
}
