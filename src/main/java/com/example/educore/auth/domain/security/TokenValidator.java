package com.example.educore.auth.domain.security;

import java.util.Optional;

public interface TokenValidator {
    Optional<TokenClaims> validate(String token);
}
