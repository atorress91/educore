package com.example.educore.teachers.application.queries;

import com.example.educore.sharedkernel.application.Query;
import com.example.educore.teachers.application.dto.TeacherAssignmentResponse;

import java.util.List;

public record GetAllAssignmentsQuery() implements Query<List<TeacherAssignmentResponse>> {}
