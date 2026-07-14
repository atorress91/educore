package com.example.educore.courses.domain.exceptions;

import com.example.educore.sharedkernel.domain.DomainException;

public class CourseException extends DomainException {

    private CourseException(String code, String message) {
        super(code, message);
    }

    public static CourseException noActiveYear() {
        return new CourseException("COURSE_NO_ACTIVE_YEAR", "No hay un curso lectivo activo");
    }

    public static CourseException yearAlreadyExists(int year) {
        return new CourseException("COURSE_YEAR_DUPLICATE", "Ya existe el curso lectivo " + year);
    }

    public static CourseException activeYearExists() {
        return new CourseException("COURSE_ACTIVE_YEAR_EXISTS",
                "Ya hay un curso lectivo activo; ciérrelo antes de abrir otro");
    }

    public static CourseException notFound() {
        return new CourseException("COURSE_NOT_FOUND", "Curso lectivo no encontrado");
    }

    public static CourseException levelNotConfigured(int level) {
        return new CourseException("COURSE_LEVEL_NOT_CONFIGURED",
                "El nivel " + level + " no está configurado en el curso lectivo");
    }
}
