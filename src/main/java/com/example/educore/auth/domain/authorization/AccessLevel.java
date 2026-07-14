package com.example.educore.auth.domain.authorization;

/**
 * Access a role has over a system module.
 * WRITE implies READ. NONE is omitted (the module is hidden).
 */
public enum AccessLevel {
    NONE,
    READ,
    WRITE
}
