package com.example.educore.courses.application.dto;

import com.example.educore.courses.domain.model.AcademicYear;
import com.example.educore.courses.domain.model.AcademicYearStatus;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public record AcademicYearResponse(
        UUID id,
        int year,
        AcademicYearStatus status,
        String startDate,
        String endDate,
        String modality,
        List<LevelPlanResponse> levels
) {
    public static AcademicYearResponse fromAcademicYear(AcademicYear academicYear) {
        List<LevelPlanResponse> levels = academicYear.getLevels().stream()
                .sorted(Comparator.comparingInt(plan -> plan.getLevel().number()))
                .map(LevelPlanResponse::fromLevelPlan)
                .toList();

        return new AcademicYearResponse(
                academicYear.getId(),
                academicYear.getYear(),
                academicYear.getStatus(),
                academicYear.getStartDate().toString(),
                academicYear.getEndDate().toString(),
                academicYear.getModality(),
                levels
        );
    }
}
