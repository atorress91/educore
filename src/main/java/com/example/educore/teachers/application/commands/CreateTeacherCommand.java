package com.example.educore.teachers.application.commands;

import com.example.educore.sharedkernel.application.Command;
import com.example.educore.teachers.application.dto.TeacherResponse;
import com.example.educore.teachers.domain.model.TeacherStatus;

import java.time.LocalDate;

public record CreateTeacherCommand(
        String fullName,
        String email,
        String phone,
        String subject,
        LocalDate hiredAt,
        TeacherStatus status
) implements Command<TeacherResponse> {}
