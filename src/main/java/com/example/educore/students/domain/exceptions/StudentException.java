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
}
