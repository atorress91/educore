package com.example.educore.grades.application.queries;

import com.example.educore.grades.application.dto.GradeEntryResponse;
import com.example.educore.sharedkernel.application.Query;
import com.example.educore.sharedkernel.domain.Level;

import java.util.List;

public record GetGradesQuery(
        Level level,
        String section,
        String subject
) implements Query<List<GradeEntryResponse>> {}
