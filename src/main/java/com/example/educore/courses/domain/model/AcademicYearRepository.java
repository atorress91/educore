package com.example.educore.courses.domain.model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AcademicYearRepository {

    AcademicYear save(AcademicYear academicYear);

    Optional<AcademicYear> findById(UUID id);

    Optional<AcademicYear> findFirstByStatusOrderByYearDesc(AcademicYearStatus status);

    List<AcademicYear> findAllByOrderByYearDesc();

    boolean existsByYear(int year);
}
