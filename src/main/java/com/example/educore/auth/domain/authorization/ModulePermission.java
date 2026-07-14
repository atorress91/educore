package com.example.educore.auth.domain.authorization;

/**
 * Permiso efectivo de un rol sobre un módulo. Se serializa en GraphQL
 * como el tipo {@code ModulePermission { module, level }}.
 */
public record ModulePermission(AppModule module, AccessLevel level) {}
