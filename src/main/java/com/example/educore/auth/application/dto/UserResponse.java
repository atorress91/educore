package com.example.educore.auth.application.dto;

import com.example.educore.auth.domain.authorization.ModulePermission;
import com.example.educore.auth.domain.authorization.RolePermissions;
import com.example.educore.auth.domain.model.Role;
import com.example.educore.auth.domain.model.User;

import java.util.List;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String lastName,
        String email,
        String imageUrl,
        Role role,
        List<ModulePermission> permissions
) {
    public static UserResponse fromUser(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getEmail(),
                user.getImageUrl(),
                user.getRole(),
                RolePermissions.forRole(user.getRole())
        );
    }
}
