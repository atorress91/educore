package com.example.educore.grades.application.dto;

import java.util.UUID;

public record GradeEntryResponse(
        UUID studentId,
        Integer trim1,
        Integer trim2,
        Integer trim3,
        Integer ampl1,
        Integer ampl2
) {}
