package com.example.educore.auth.application.queries;

import com.example.educore.auth.application.dto.UserResponse;
import com.example.educore.sharedkernel.application.Query;

import java.util.UUID;

public record GetCurrentUserQuery(UUID userId) implements Query<UserResponse> {}
