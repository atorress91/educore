package com.example.educore.auth.application.commands;

import com.example.educore.auth.application.dto.UserResponse;
import com.example.educore.auth.domain.model.Role;
import com.example.educore.sharedkernel.application.Command;

import java.util.UUID;

public record PromoteUserCommand(UUID userId, Role newRole) implements Command<UserResponse> {}
