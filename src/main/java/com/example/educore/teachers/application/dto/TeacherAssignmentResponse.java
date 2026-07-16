package com.example.educore.teachers.application.dto;

import com.example.educore.sharedkernel.domain.Level;

import java.util.UUID;

public record TeacherAssignmentResponse(
        Level level,
        String section,
        String subject,
        UUID teacherId,
        String teacherName
) {}
