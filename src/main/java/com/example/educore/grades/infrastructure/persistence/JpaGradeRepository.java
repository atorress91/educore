package com.example.educore.grades.infrastructure.persistence;

import com.example.educore.grades.domain.model.GradeRecord;
import com.example.educore.grades.domain.model.GradeRepository;
import com.example.educore.sharedkernel.domain.Level;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaGradeRepository extends JpaRepository<GradeRecord, UUID>, GradeRepository {

    List<GradeRecord> findByYearAndLevelAndSectionAndSubject(
            int year, Level level, String section, String subject);

    List<GradeRecord> findByYearAndLevelAndSection(int year, Level level, String section);

    Optional<GradeRecord> findByYearAndLevelAndSectionAndSubjectAndStudentId(
            int year, Level level, String section, String subject, UUID studentId);
}
