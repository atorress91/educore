package com.example.educore.attendance.infrastructure.graphql;

import com.example.educore.attendance.domain.model.AttendanceStatus;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AttendanceEntryInput(
        @NotNull UUID studentId,
        @NotNull AttendanceStatus status
) {}
