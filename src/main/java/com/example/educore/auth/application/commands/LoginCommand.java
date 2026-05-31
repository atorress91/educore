package com.example.educore.auth.application.commands;

import com.example.educore.auth.application.dto.AuthResponse;
import com.example.educore.sharedkernel.application.Command;

public record LoginCommand(String email, String password) implements Command<AuthResponse> {}
