package com.example.educore.auth.domain.authorization;

import com.example.educore.auth.domain.model.Role;

/** A single (role, module) → level assignment coming from the admin editor. */
public record RolePermissionEntry(Role role, AppModule module, AccessLevel level) {}
