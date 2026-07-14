package com.example.educore.courses.application.commands;

import com.example.educore.courses.application.dto.AcademicYearResponse;
import com.example.educore.sharedkernel.application.Command;
import com.example.educore.sharedkernel.domain.Level;

public record RemoveSubjectFromLevelCommand(Level level, String subject) implements Command<AcademicYearResponse> {}
