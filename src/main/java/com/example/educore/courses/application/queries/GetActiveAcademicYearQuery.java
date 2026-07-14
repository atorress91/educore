package com.example.educore.courses.application.queries;

import com.example.educore.courses.application.dto.AcademicYearResponse;
import com.example.educore.sharedkernel.application.Query;

public record GetActiveAcademicYearQuery() implements Query<AcademicYearResponse> {}
