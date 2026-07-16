package com.example.educore.teachers.application.dto;

import com.example.educore.sharedkernel.domain.Level;

/** A group the teacher is responsible for: a subject taught in a level+section. */
public record TeacherGroupResponse(
        Level level,
        String section,
        String subject
) {}
