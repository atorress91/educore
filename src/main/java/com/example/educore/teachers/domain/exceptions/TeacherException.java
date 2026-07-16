package com.example.educore.teachers.domain.exceptions;

import com.example.educore.sharedkernel.domain.DomainException;

public class TeacherException extends DomainException {

    private TeacherException(String code, String message) {
        super(code, message);
    }

    public static TeacherException notFound() {
        return new TeacherException("TEACHER_NOT_FOUND", "Profesor no encontrado");
    }

    public static TeacherException invalidSubject(String subject) {
        return new TeacherException(
                "TEACHER_INVALID_SUBJECT",
                "La materia '" + subject + "' no pertenece al plan del curso lectivo");
    }

    public static TeacherException invalidSection(String section) {
        return new TeacherException(
                "TEACHER_INVALID_SECTION",
                "La sección '" + section + "' no existe para ese nivel en el curso lectivo");
    }
}
