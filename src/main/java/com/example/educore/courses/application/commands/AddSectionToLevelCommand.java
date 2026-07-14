package com.example.educore.courses.application.commands;

import com.example.educore.courses.application.dto.AcademicYearResponse;
import com.example.educore.sharedkernel.application.Command;
import com.example.educore.sharedkernel.domain.Level;

public record AddSectionToLevelCommand(Level level, String section) implements Command<AcademicYearResponse> {}
