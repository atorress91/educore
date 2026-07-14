package com.example.educore.courses.infrastructure.persistence;

import com.example.educore.courses.domain.model.AcademicYear;
import com.example.educore.courses.domain.model.AcademicYearRepository;
import com.example.educore.courses.domain.model.DefaultCurriculum;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Seeds an active academic year (with the default per-level plans) plus a
 * couple of closed years for the history section. Idempotent. Runs before the
 * student seeder so enrollment has an active year to validate against.
 */
@Component
@Order(1)
@RequiredArgsConstructor
class AcademicYearSeeder implements CommandLineRunner {

    private final AcademicYearRepository academicYearRepository;

    @Override
    public void run(String... args) {
        seedClosed(2024);
        seedClosed(2025);
        if (!academicYearRepository.existsByYear(2026)) {
            academicYearRepository.save(AcademicYear.open(
                    2026, LocalDate.of(2026, 2, 3), LocalDate.of(2026, 11, 30), "Nocturna", DefaultCurriculum.plans()));
        }
    }

    private void seedClosed(int year) {
        if (academicYearRepository.existsByYear(year)) {
            return;
        }
        AcademicYear closed = AcademicYear.open(
                year, LocalDate.of(year, 2, 1), LocalDate.of(year, 11, 30), "Nocturna", DefaultCurriculum.plans());
        closed.close();
        academicYearRepository.save(closed);
    }
}
