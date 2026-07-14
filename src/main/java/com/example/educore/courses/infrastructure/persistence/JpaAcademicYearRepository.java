package com.example.educore.courses.infrastructure.persistence;

import com.example.educore.courses.domain.model.AcademicYear;
import com.example.educore.courses.domain.model.AcademicYearRepository;
import com.example.educore.courses.domain.model.AcademicYearStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaAcademicYearRepository extends JpaRepository<AcademicYear, UUID>, AcademicYearRepository {

    Optional<AcademicYear> findFirstByStatusOrderByYearDesc(AcademicYearStatus status);

    List<AcademicYear> findAllByOrderByYearDesc();

    boolean existsByYear(int year);
}
