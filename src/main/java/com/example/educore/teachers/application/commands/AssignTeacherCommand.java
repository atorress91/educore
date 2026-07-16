package com.example.educore.teachers.application.commands;

import com.example.educore.sharedkernel.application.Command;
import com.example.educore.sharedkernel.domain.Level;
import com.example.educore.teachers.application.dto.TeacherAssignmentResponse;

import java.util.UUID;

public record AssignTeacherCommand(
        Level level,
        String section,
        String subject,
        UUID teacherId
) implements Command<TeacherAssignmentResponse> {}
