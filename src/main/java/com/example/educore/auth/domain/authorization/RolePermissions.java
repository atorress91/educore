package com.example.educore.auth.domain.authorization;

import com.example.educore.auth.domain.model.Role;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Matriz de autorización: qué nivel de acceso (READ/WRITE) tiene cada rol
 * sobre cada módulo del sistema. Este es el ÚNICO lugar a editar para
 * cambiar los permisos de una vista.
 *
 * <ul>
 *   <li>ADMIN y ADMINISTRATIVO: escritura total.</li>
 *   <li>TEACHER (profesor): escribe Asistencia y Calificaciones; el resto lo ve.</li>
 *   <li>STUDENT: sin acceso al dashboard (solo es un registro de datos).</li>
 * </ul>
 */
public final class RolePermissions {

    private static final Map<Role, Map<AppModule, AccessLevel>> MATRIX = buildMatrix();

    private RolePermissions() {}

    /** Permisos efectivos del rol, ordenados por módulo, omitiendo los NONE. */
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
        matrix.put(Role.ADMINISTRATIVO, allWrite());

        Map<AppModule, AccessLevel> teacher = new EnumMap<>(AppModule.class);
        teacher.put(AppModule.DASHBOARD, AccessLevel.READ);
        teacher.put(AppModule.ESTUDIANTES, AccessLevel.READ);
        teacher.put(AppModule.PROFESORES, AccessLevel.NONE);
        teacher.put(AppModule.CURSOS, AccessLevel.READ);
        teacher.put(AppModule.ASISTENCIA, AccessLevel.WRITE);
        teacher.put(AppModule.CALIFICACIONES, AccessLevel.WRITE);
        teacher.put(AppModule.CERTIFICADOS, AccessLevel.NONE);
        teacher.put(AppModule.REPORTES, AccessLevel.READ);
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
