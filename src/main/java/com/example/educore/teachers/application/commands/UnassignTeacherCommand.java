package com.example.educore.teachers.application.commands;

import com.example.educore.sharedkernel.application.Command;
import com.example.educore.sharedkernel.domain.Level;

public record UnassignTeacherCommand(
        Level level,
        String section,
        String subject
) implements Command<Boolean> {}
