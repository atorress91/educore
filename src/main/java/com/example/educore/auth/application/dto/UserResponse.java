package com.example.educore.auth.application.dto;

import com.example.educore.auth.domain.model.Role;
import com.example.educore.auth.domain.model.User;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String lastName,
        String email,
        String imageUrl,
        Role role
) {
    public static UserResponse fromUser(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getEmail(),
                user.getImageUrl(),
                user.getRole()
        );
    }
}
