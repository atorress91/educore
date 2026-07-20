package com.example.educore.auth.infrastructure.security;

import com.example.educore.auth.application.authorization.RolePermissionService;
import com.example.educore.auth.domain.authorization.AccessLevel;
import com.example.educore.auth.domain.authorization.AppModule;
import com.example.educore.auth.domain.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Method-security helper exposed to SpEL as {@code @perm}. Enforces the dynamic
 * permission matrix on GraphQL operations, e.g.
 * {@code @PreAuthorize("@perm.can('STUDENTS','WRITE')")}.
 *
 * <p>Referenced only as a string in {@code @PreAuthorize}, so the business
 * modules never import this class — no cross-module dependency is introduced.</p>
 */
@Component("perm")
@RequiredArgsConstructor
public class PermissionGuard {

    private final RolePermissionService rolePermissionService;

    /** True when the current user's role grants at least {@code level} on {@code module}. */
    public boolean can(String module, String level) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        Role role = roleOf(authentication);
        if (role == null) {
            return false;
        }
        if (role == Role.ADMIN) {
            return true; // admin always has full access
        }
        AppModule target = AppModule.valueOf(module);
        AccessLevel required = AccessLevel.valueOf(level);
        AccessLevel granted = rolePermissionService.effectiveFor(role).stream()
                .filter(mp -> mp.module() == target)
                .map(mp -> mp.level())
                .findFirst()
                .orElse(AccessLevel.NONE);
        return satisfies(granted, required);
    }

    private boolean satisfies(AccessLevel granted, AccessLevel required) {
        if (required == AccessLevel.WRITE) {
            return granted == AccessLevel.WRITE;
        }
        return granted != AccessLevel.NONE; // READ is satisfied by READ or WRITE
    }

    private Role roleOf(Authentication authentication) {
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String value = authority.getAuthority();
            if (value.startsWith("ROLE_")) {
                try {
                    return Role.valueOf(value.substring("ROLE_".length()));
                } catch (IllegalArgumentException ignored) {
                    // not a known role authority; keep scanning
                }
            }
        }
        return null;
    }
}
