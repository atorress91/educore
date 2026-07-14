package com.example.educore.auth.domain.authorization;

/**
 * Módulos (vistas) del sistema que corresponden a las secciones del dashboard.
 * Se expone en GraphQL como el enum {@code Module}.
 */
public enum AppModule {
    DASHBOARD,
    ESTUDIANTES,
    PROFESORES,
    CURSOS,
    ASISTENCIA,
    CALIFICACIONES,
    CERTIFICADOS,
    REPORTES
}
