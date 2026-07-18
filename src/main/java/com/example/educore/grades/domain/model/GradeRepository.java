package com.example.educore.grades.domain.model;

import com.example.educore.sharedkernel.domain.Level;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GradeRepository {

    GradeRecord save(GradeRecord record);

    List<GradeRecord> findByYearAndLevelAndSectionAndSubject(
            int year, Level level, String section, String subject);

    List<GradeRecord> findByYearAndLevelAndSection(int year, Level level, String section);

    Optional<GradeRecord> findByYearAndLevelAndSectionAndSubjectAndStudentId(
            int year, Level level, String section, String subject, UUID studentId);
}
