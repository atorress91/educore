package com.example.educore.auth.domain.authorization;

import com.example.educore.auth.domain.model.Role;

import java.util.List;
import java.util.Optional;

public interface RolePermissionRepository {

    List<RolePermissionSetting> findAll();

    List<RolePermissionSetting> findByRole(Role role);

    Optional<RolePermissionSetting> findByRoleAndModule(Role role, AppModule module);

    RolePermissionSetting save(RolePermissionSetting setting);

    long count();
}
