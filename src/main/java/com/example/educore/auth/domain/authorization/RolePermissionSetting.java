package com.example.educore.auth.domain.authorization;

import com.example.educore.auth.domain.model.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Persisted access level of a role over a module. Replaces the previously
 * static {@link RolePermissions} matrix so ADMIN can edit permissions at
 * runtime. There is one row per (role, module) pair.
 */
@Entity
@Table(
        name = "role_permissions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"role", "module"})
)
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class RolePermissionSetting {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "module", nullable = false)
    private AppModule module;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccessLevel level;

    private RolePermissionSetting(Role role, AppModule module, AccessLevel level) {
        this.id = UUID.randomUUID();
        this.role = role;
        this.module = module;
        this.level = level;
    }

    public static RolePermissionSetting of(Role role, AppModule module, AccessLevel level) {
        return new RolePermissionSetting(role, module, level);
    }

    public void changeLevel(AccessLevel newLevel) {
        this.level = newLevel;
    }
}
