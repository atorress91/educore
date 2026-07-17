package com.example.educore.grades.infrastructure.graphql;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/** A single student's grade row. Trimester/recovery scores are 0-100 or null. */
public record GradeEntryInput(
        @NotNull UUID studentId,
        @Min(0) @Max(100) Integer trim1,
        @Min(0) @Max(100) Integer trim2,
        @Min(0) @Max(100) Integer trim3,
        @Min(0) @Max(100) Integer ampl1,
        @Min(0) @Max(100) Integer ampl2
) {}
