package com.example.educore.auth.application.authorization;

import com.example.educore.auth.application.dto.RolePermissionsView;
import com.example.educore.auth.domain.authorization.AccessLevel;
import com.example.educore.auth.domain.authorization.AppModule;
import com.example.educore.auth.domain.authorization.ModulePermission;
import com.example.educore.auth.domain.authorization.RolePermissionEntry;
import com.example.educore.auth.domain.authorization.RolePermissionRepository;
import com.example.educore.auth.domain.authorization.RolePermissionSetting;
import com.example.educore.auth.domain.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Reads and edits the persisted role/module permission matrix.
 *
 * <p>ADMIN is special-cased: it always has WRITE on every module and its row is
 * never stored or editable, so an admin can never lock itself out. STUDENT has
 * no dashboard, so it is not part of the editable matrix. Only STAFF and TEACHER
 * are configurable.</p>
 */
@Service
@RequiredArgsConstructor
public class RolePermissionService {

    /** Roles shown in the admin editor, in display order (ADMIN first, locked). */
    private static final List<Role> MANAGED_ROLES = List.of(Role.ADMIN, Role.STAFF, Role.TEACHER);

    /** Roles whose permissions the admin may actually change. */
    private static final List<Role> EDITABLE_ROLES = List.of(Role.STAFF, Role.TEACHER);

    private final RolePermissionRepository repository;

    /** Effective permissions for a user's role (omitting NONE), for login/me. */
    @Transactional(readOnly = true)
    public List<ModulePermission> effectiveFor(Role role) {
        if (role == Role.ADMIN) {
            return allWrite();
        }
        Map<AppModule, AccessLevel> levels = levelsFor(role);
        return levels.entrySet().stream()
                .filter(entry -> entry.getValue() != AccessLevel.NONE)
                .map(entry -> new ModulePermission(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparingInt(mp -> mp.module().ordinal()))
                .toList();
    }

    /** The full editable matrix for the admin view (every module, including NONE). */
    @Transactional(readOnly = true)
    public List<RolePermissionsView> matrix() {
        return MANAGED_ROLES.stream()
                .map(role -> new RolePermissionsView(role, fullRow(role)))
                .toList();
    }

    /** Applies the admin's edits, ignoring any entry for a non-editable role. */
    @Transactional
    public List<RolePermissionsView> update(List<RolePermissionEntry> entries) {
        for (RolePermissionEntry entry : entries) {
            if (!EDITABLE_ROLES.contains(entry.role())) {
                continue;
            }
            AccessLevel level = entry.level() == null ? AccessLevel.NONE : entry.level();
            repository.findByRoleAndModule(entry.role(), entry.module())
                    .ifPresentOrElse(
                            setting -> setting.changeLevel(level),
                            () -> repository.save(RolePermissionSetting.of(entry.role(), entry.module(), level))
                    );
        }
        return matrix();
    }

    private List<ModulePermission> fullRow(Role role) {
        if (role == Role.ADMIN) {
            return allWrite();
        }
        Map<AppModule, AccessLevel> levels = levelsFor(role);
        return java.util.Arrays.stream(AppModule.values())
                .map(module -> new ModulePermission(module, levels.getOrDefault(module, AccessLevel.NONE)))
                .toList();
    }

    private Map<AppModule, AccessLevel> levelsFor(Role role) {
        Map<AppModule, AccessLevel> levels = new EnumMap<>(AppModule.class);
        for (RolePermissionSetting setting : repository.findByRole(role)) {
            levels.put(setting.getModule(), setting.getLevel());
        }
        return levels;
    }

    private List<ModulePermission> allWrite() {
        return java.util.Arrays.stream(AppModule.values())
                .map(module -> new ModulePermission(module, AccessLevel.WRITE))
                .toList();
    }
}
