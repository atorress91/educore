package com.example.educore.auth.application.dto;

import com.example.educore.auth.domain.authorization.ModulePermission;
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
    /** Builds the response with the role's effective (dynamic) permissions. */
    public static UserResponse of(User user, List<ModulePermission> permissions) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getEmail(),
                user.getImageUrl(),
                user.getRole(),
                permissions
        );
    }
}
