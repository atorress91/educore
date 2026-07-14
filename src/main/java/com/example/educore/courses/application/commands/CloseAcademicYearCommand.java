package com.example.educore.courses.application.commands;

import com.example.educore.courses.application.dto.AcademicYearResponse;
import com.example.educore.sharedkernel.application.Command;

import java.util.UUID;

public record CloseAcademicYearCommand(UUID academicYearId) implements Command<AcademicYearResponse> {}
