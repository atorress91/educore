package com.example.educore.auth.application.commands;

import com.example.educore.auth.domain.model.Role;

import java.util.UUID;

public record PromoteUserCommand(UUID userId, Role newRole) {}
