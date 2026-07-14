package com.example.educore.auth.domain.authorization;

/**
 * Effective permission of a role over a module. Serialized in GraphQL
 * as the {@code ModulePermission { module, level }} type.
 */
public record ModulePermission(AppModule module, AccessLevel level) {}
