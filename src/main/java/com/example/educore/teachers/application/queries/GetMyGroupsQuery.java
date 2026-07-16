package com.example.educore.teachers.application.queries;

import com.example.educore.sharedkernel.application.Query;
import com.example.educore.teachers.application.dto.TeacherGroupResponse;

import java.util.List;
import java.util.UUID;

public record GetMyGroupsQuery(UUID userId) implements Query<List<TeacherGroupResponse>> {}
