package com.example.educore.auth.domain.authorization;

import com.example.educore.auth.domain.model.Role;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Authorization matrix: which access level (READ/WRITE) each role has over
 * each system module. This is the ONLY place to edit a view's permissions.
 *
 * <ul>
 *   <li>ADMIN and STAFF: full write access.</li>
 *   <li>TEACHER: writes Attendance and Grades; reads the rest.</li>
 *   <li>STUDENT: no dashboard access (just a data record).</li>
 * </ul>
 */
public final class RolePermissions {

    private static final Map<Role, Map<AppModule, AccessLevel>> MATRIX = buildMatrix();

    private RolePermissions() {}

    /** Effective permissions for the role, ordered by module, omitting NONE. */
    public static List<ModulePermission> forRole(Role role) {
        return MATRIX.getOrDefault(role, Map.of()).entrySet().stream()
                .filter(entry -> entry.getValue() != AccessLevel.NONE)
                .map(entry -> new ModulePermission(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparingInt(mp -> mp.module().ordinal()))
                .toList();
    }

    private static Map<Role, Map<AppModule, AccessLevel>> buildMatrix() {
        Map<Role, Map<AppModule, AccessLevel>> matrix = new EnumMap<>(Role.class);

        matrix.put(Role.ADMIN, allWrite());
        matrix.put(Role.STAFF, allWrite());

        Map<AppModule, AccessLevel> teacher = new EnumMap<>(AppModule.class);
        teacher.put(AppModule.DASHBOARD, AccessLevel.READ);
        teacher.put(AppModule.STUDENTS, AccessLevel.READ);
        teacher.put(AppModule.TEACHERS, AccessLevel.NONE);
        teacher.put(AppModule.COURSES, AccessLevel.READ);
        teacher.put(AppModule.ATTENDANCE, AccessLevel.WRITE);
        teacher.put(AppModule.GRADES, AccessLevel.WRITE);
        teacher.put(AppModule.CERTIFICATES, AccessLevel.NONE);
        teacher.put(AppModule.REPORTS, AccessLevel.READ);
        matrix.put(Role.TEACHER, teacher);

        matrix.put(Role.STUDENT, Map.of());

        return matrix;
    }

    private static Map<AppModule, AccessLevel> allWrite() {
        Map<AppModule, AccessLevel> all = new EnumMap<>(AppModule.class);
        for (AppModule module : AppModule.values()) {
            all.put(module, AccessLevel.WRITE);
        }
        return all;
    }
}
