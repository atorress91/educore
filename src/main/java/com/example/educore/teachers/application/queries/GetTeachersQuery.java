package com.example.educore.teachers.application.queries;

import com.example.educore.sharedkernel.application.Query;
import com.example.educore.teachers.application.dto.TeacherResponse;

import java.util.List;

public record GetTeachersQuery() implements Query<List<TeacherResponse>> {}
