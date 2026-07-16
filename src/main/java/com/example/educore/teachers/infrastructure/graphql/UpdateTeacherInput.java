package com.example.educore.teachers.infrastructure.graphql;

import com.example.educore.teachers.domain.model.TeacherStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateTeacherInput(
        @NotBlank String fullName,
        @NotBlank @Email String email,
        @NotBlank String phone,
        @NotBlank String subject,
        @NotBlank String hiredAt,
        @NotNull TeacherStatus status
) {}
