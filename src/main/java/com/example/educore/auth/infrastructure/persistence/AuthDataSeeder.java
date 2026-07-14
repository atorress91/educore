package com.example.educore.auth.infrastructure.persistence;

import com.example.educore.auth.domain.model.Role;
import com.example.educore.auth.domain.model.User;
import com.example.educore.auth.domain.model.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Seeds staff users (with dashboard access) on startup so login works against
 * the in-memory H2 database. Idempotent: it never duplicates existing users.
 * Students are NOT seeded here because they don't log in (they are just data).
 */
@Component
@RequiredArgsConstructor
class AuthDataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        seed("Sistema", "Admin", "0000001", "admin@liceojjjn.edu.cr", "admin123", Role.ADMIN);
        seed("Ana", "Administrativa", "0000002", "administrativo@liceojjjn.edu.cr", "admin123", Role.STAFF);
        seed("Carlos", "Ruiz", "0000003", "docente@liceojjjn.edu.cr", "docente123", Role.TEACHER);
    }

    private void seed(String name, String lastName, String identification, String email, String rawPassword, Role role) {
        if (userRepository.existsByEmail(email)) {
            return;
        }
        userRepository.save(
                User.register(name, lastName, identification, email, passwordEncoder.encode(rawPassword), role)
        );
    }
}
