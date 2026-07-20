package com.example.educore.auth.application.commands;

import com.example.educore.auth.application.dto.RolePermissionsView;
import com.example.educore.auth.domain.authorization.RolePermissionEntry;
import com.example.educore.sharedkernel.application.Command;

import java.util.List;

/** Applies the admin's edits to the permission matrix and returns it fresh. */
public record UpdateRolePermissionsCommand(List<RolePermissionEntry> entries)
        implements Command<List<RolePermissionsView>> {}
