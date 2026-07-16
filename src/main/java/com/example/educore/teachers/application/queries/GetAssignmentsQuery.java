package com.example.educore.teachers.application.queries;

import com.example.educore.sharedkernel.application.Query;
import com.example.educore.sharedkernel.domain.Level;
import com.example.educore.teachers.application.dto.TeacherAssignmentResponse;

import java.util.List;

public record GetAssignmentsQuery(Level level) implements Query<List<TeacherAssignmentResponse>> {}
