package com.example.educore.auth.infrastructure.graphql;

import com.example.educore.auth.domain.model.Role;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PromoteUserInput(
        @NotNull UUID userId,
        @NotNull Role newRole
) {}
