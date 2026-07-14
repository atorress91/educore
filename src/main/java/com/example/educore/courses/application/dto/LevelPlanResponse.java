package com.example.educore.courses.application.dto;

import com.example.educore.courses.domain.model.LevelPlan;
import com.example.educore.sharedkernel.domain.Level;

import java.util.List;

public record LevelPlanResponse(
        Level level,
        List<String> sections,
        List<String> subjects
) {
    public static LevelPlanResponse fromLevelPlan(LevelPlan plan) {
        return new LevelPlanResponse(plan.getLevel(), plan.getSections(), plan.getSubjects());
    }
}
