package com.example.educore.courses.application.commands;

import com.example.educore.courses.application.dto.AcademicYearResponse;
import com.example.educore.sharedkernel.application.Command;

import java.time.LocalDate;

public record CreateAcademicYearCommand(
        int year,
        LocalDate startDate,
        LocalDate endDate
) implements Command<AcademicYearResponse> {}
