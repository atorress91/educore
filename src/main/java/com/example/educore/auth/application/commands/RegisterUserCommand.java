package com.example.educore.auth.application.commands;

public record RegisterUserCommand(
        String name,
        String lastName,
        String identification,
        String email,
        String password
) {}
