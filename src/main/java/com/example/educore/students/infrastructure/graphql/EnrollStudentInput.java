package com.example.educore.students.infrastructure.graphql;

import com.example.educore.sharedkernel.domain.Level;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

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
        @NotBlank String guardianPhone,
        @NotEmpty List<String> subjects
) {}
