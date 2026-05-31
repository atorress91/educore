package com.example.educore.auth.infrastructure.persistence;

import com.example.educore.auth.domain.model.User;
import com.example.educore.auth.domain.model.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<User, UUID>, UserRepository {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
