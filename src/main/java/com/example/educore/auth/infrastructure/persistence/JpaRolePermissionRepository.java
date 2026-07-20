package com.example.educore.auth.infrastructure.persistence;

import com.example.educore.auth.domain.authorization.AppModule;
import com.example.educore.auth.domain.authorization.RolePermissionRepository;
import com.example.educore.auth.domain.authorization.RolePermissionSetting;
import com.example.educore.auth.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaRolePermissionRepository
        extends JpaRepository<RolePermissionSetting, UUID>, RolePermissionRepository {

    List<RolePermissionSetting> findByRole(Role role);

    Optional<RolePermissionSetting> findByRoleAndModule(Role role, AppModule module);
}
