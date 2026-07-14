package com.example.educore.students.infrastructure.graphql;

import com.example.educore.students.domain.model.Level;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnrollStudentInput(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String identification,
        @NotBlank String phone,
        @NotNull Level level,
        @NotBlank String section,
        @NotBlank String province,
        @NotBlank String canton,
        @NotBlank String district,
        @NotBlank String address,
        @NotBlank String guardianName,
        @NotBlank String guardianPhone
) {}
