package com.example.educore.auth.domain.authorization;

/**
 * Nivel de acceso que un rol tiene sobre un módulo del sistema.
 * WRITE implica READ. NONE se omite (el módulo no se muestra).
 */
public enum AccessLevel {
    NONE,
    READ,
    WRITE
}
