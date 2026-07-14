package com.example.educore.auth.infrastructure.persistence;

import com.example.educore.auth.domain.model.Role;
import com.example.educore.auth.domain.model.User;
import com.example.educore.auth.domain.model.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Siembra usuarios de staff (con acceso al dashboard) al arrancar, para que
 * el login funcione contra la base H2 en memoria. Es idempotente: no duplica
 * usuarios ya existentes. Los estudiantes NO se siembran aquí porque no
 * inician sesión (son solo registros de datos).
 */
@Component
@RequiredArgsConstructor
class AuthDataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        seed("Sistema", "Admin", "0000001", "admin@liceojjjn.edu.cr", "admin123", Role.ADMIN);
        seed("Ana", "Administrativa", "0000002", "administrativo@liceojjjn.edu.cr", "admin123", Role.ADMINISTRATIVO);
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
