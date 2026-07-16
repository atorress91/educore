package com.example.educore.auth;

import java.util.Optional;
import java.util.UUID;

/**
 * Public API of the Auth module, consumed by other modules that need to resolve
 * the currently authenticated user (e.g. Teachers maps the logged-in account to
 * a Teacher by email). Lives in the module's base package so it is exposed
 * across module boundaries.
 */
public interface AuthApi {

    /** Email of the user with the given id, if it exists. */
    Optional<String> emailOf(UUID userId);
}
