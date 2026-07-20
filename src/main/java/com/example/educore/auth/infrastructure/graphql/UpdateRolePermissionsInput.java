package com.example.educore.auth.infrastructure.graphql;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UpdateRolePermissionsInput(@NotNull List<RolePermissionEntryInput> entries) {}
