package com.example.educore.auth.application.dto;

import com.example.educore.auth.domain.authorization.ModulePermission;
import com.example.educore.auth.domain.model.Role;

import java.util.List;

/**
 * Full permission row for a single role: every module with its current level
 * (including NONE). Serialized in GraphQL as {@code RolePermissions}.
 */
public record RolePermissionsView(Role role, List<ModulePermission> modules) {}
