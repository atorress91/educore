package com.example.educore.grades.application.queries;

import com.example.educore.grades.application.dto.SectionGradeResponse;
import com.example.educore.sharedkernel.application.Query;
import com.example.educore.sharedkernel.domain.Level;

import java.util.List;

public record GetSectionGradesQuery(
        Level level,
        String section
) implements Query<List<SectionGradeResponse>> {}
