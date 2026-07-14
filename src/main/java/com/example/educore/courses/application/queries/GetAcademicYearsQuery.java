package com.example.educore.courses.application.queries;

import com.example.educore.courses.application.dto.AcademicYearResponse;
import com.example.educore.sharedkernel.application.Query;

import java.util.List;

public record GetAcademicYearsQuery() implements Query<List<AcademicYearResponse>> {}
