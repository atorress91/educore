package com.example.educore.auth.infrastructure.persistence;

import com.example.educore.auth.domain.authorization.AppModule;
import com.example.educore.auth.domain.authorization.RolePermissionRepository;
import com.example.educore.auth.domain.authorization.RolePermissionSetting;
import com.example.educore.auth.domain.authorization.RolePermissions;
import com.example.educore.auth.domain.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Seeds the persisted permission matrix from the default {@link RolePermissions}
 * on first startup. Only the editable roles (STAFF, TEACHER) get rows; ADMIN is
 * always full-write in code and STUDENT has no dashboard. Idempotent: it skips
 * seeding when the table already has rows, so admin edits survive restarts as
 * long as the database does (H2 in-memory resets, Postgres persists).
 */
@Component
@RequiredArgsConstructor
class RolePermissionSeeder implements CommandLineRunner {

    private static final List<Role> EDITABLE_ROLES = List.of(Role.STAFF, Role.TEACHER);

    private final RolePermissionRepository repository;

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            return;
        }
        for (Role role : EDITABLE_ROLES) {
            for (AppModule module : AppModule.values()) {
                repository.save(
                        RolePermissionSetting.of(role, module, RolePermissions.defaultLevel(role, module))
                );
            }
        }
    }
}
