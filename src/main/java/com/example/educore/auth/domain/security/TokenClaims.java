package com.example.educore.auth.domain.security;

import java.util.UUID;

public record TokenClaims(UUID userId, String role) {}
