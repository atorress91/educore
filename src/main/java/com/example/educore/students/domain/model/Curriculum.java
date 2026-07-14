package com.example.educore.students.domain.model;

import java.util.List;

/**
 * Study plan: the subjects a student is enrolled in for a given level.
 * Third cycle (7–9) and diversified education (10–11) have different plans.
 */
public final class Curriculum {

    private static final List<Subject> THIRD_CYCLE = List.of(
            Subject.SOCIAL_STUDIES,
            Subject.SCIENCE,
            Subject.MATHEMATICS,
            Subject.ENGLISH,
            Subject.SPANISH,
            Subject.ETHICS,
            Subject.ACCOUNTING,
            Subject.CIVICS
    );

    private static final List<Subject> DIVERSIFIED = List.of(
            Subject.SOCIAL_STUDIES,
            Subject.CIVICS,
            Subject.BIOLOGY,
            Subject.MATHEMATICS,
            Subject.ENGLISH,
            Subject.SPANISH
    );

    private Curriculum() {}

    public static List<Subject> forLevel(Level level) {
        return switch (level) {
            case SEVEN, EIGHT, NINE -> THIRD_CYCLE;
            case TEN, ELEVEN -> DIVERSIFIED;
        };
    }
}
