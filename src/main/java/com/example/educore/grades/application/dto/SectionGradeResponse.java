package com.example.educore.grades.application.dto;

import java.util.UUID;

/** A grade row that also carries its subject, for assembling a full grade sheet. */
public record SectionGradeResponse(
        String subject,
        UUID studentId,
        Integer trim1,
        Integer trim2,
        Integer trim3,
        Integer ampl1,
        Integer ampl2
) {}
