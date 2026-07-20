package com.example.educore.auth.infrastructure.graphql;

import com.example.educore.auth.domain.authorization.AccessLevel;
import com.example.educore.auth.domain.authorization.AppModule;
import com.example.educore.auth.domain.model.Role;

public record RolePermissionEntryInput(Role role, AppModule module, AccessLevel level) {}
