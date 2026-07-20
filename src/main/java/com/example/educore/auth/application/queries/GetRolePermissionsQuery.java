package com.example.educore.auth.application.queries;

import com.example.educore.auth.application.dto.RolePermissionsView;
import com.example.educore.sharedkernel.application.Query;

import java.util.List;

/** Reads the full editable permission matrix for the admin editor. */
public record GetRolePermissionsQuery() implements Query<List<RolePermissionsView>> {}
