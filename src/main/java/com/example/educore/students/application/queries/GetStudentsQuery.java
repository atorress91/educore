package com.example.educore.students.application.queries;

import com.example.educore.sharedkernel.application.Query;
import com.example.educore.students.application.dto.StudentResponse;

import java.util.List;

public record GetStudentsQuery() implements Query<List<StudentResponse>> {}
