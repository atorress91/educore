package com.example.educore.courses.domain.model;

import com.example.educore.sharedkernel.domain.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * Default level plans used when opening a new academic year. The school can
 * then edit sections/subjects per level from the Courses admin screen.
 * Subject names are Spanish data values (shown to users), not identifiers.
 */
public final class DefaultCurriculum {

    private static final List<String> THIRD_CYCLE = List.of(
            "Estudios Sociales", "Ciencias", "Matemáticas", "Inglés", "Español", "Ética", "Contabilidad", "Cívica");

    private static final List<String> DIVERSIFIED = List.of(
            "Estudios Sociales", "Cívica", "Biología", "Matemáticas", "Inglés", "Español");

    private DefaultCurriculum() {}

    public static List<LevelPlan> plans() {
        List<LevelPlan> plans = new ArrayList<>();
        plans.add(AcademicYear.planFor(Level.SEVEN, List.of("A", "B"), THIRD_CYCLE));
        plans.add(AcademicYear.planFor(Level.EIGHT, List.of("A", "B"), THIRD_CYCLE));
        plans.add(AcademicYear.planFor(Level.NINE, List.of("A"), THIRD_CYCLE));
        plans.add(AcademicYear.planFor(Level.TEN, List.of("A", "B"), DIVERSIFIED));
        plans.add(AcademicYear.planFor(Level.ELEVEN, List.of("A"), DIVERSIFIED));
        return plans;
    }
}
