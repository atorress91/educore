package com.example.educore.students.domain.exceptions;

import com.example.educore.sharedkernel.domain.DomainException;

public class StudentException extends DomainException {

    private StudentException(String code, String message) {
        super(code, message);
    }

    public static StudentException identificationAlreadyExists(String identification) {
        return new StudentException(
                "STUDENT_IDENTIFICATION_DUPLICATE",
                "Ya existe un estudiante con la identificación '" + identification + "'");
    }

    public static StudentException notFound() {
        return new StudentException("STUDENT_NOT_FOUND", "Estudiante no encontrado");
    }

    public static StudentException invalidSection(String section) {
        return new StudentException(
                "STUDENT_INVALID_SECTION",
                "La sección '" + section + "' no existe para ese nivel en el curso lectivo");
    }

    public static StudentException noSubjectsSelected() {
        return new StudentException(
                "STUDENT_NO_SUBJECTS",
                "Debe seleccionar al menos una materia");
    }

    public static StudentException invalidSubject(String subject) {
        return new StudentException(
                "STUDENT_INVALID_SUBJECT",
                "La materia '" + subject + "' no pertenece al plan del nivel");
    }
}
