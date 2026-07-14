package com.example.educore.courses;

import com.example.educore.sharedkernel.domain.Level;

import java.util.List;

/**
 * Public API of the Courses module, consumed by other modules (e.g. Students
 * validates enrollment against the active year's level plan). Lives in the
 * module's base package so it is exposed across module boundaries.
 */
public interface CoursesApi {

    /** The active academic year number, or throws if none is active. */
    int activeYear();

    /** Subjects offered for the level in the active year. */
    List<String> subjectsFor(Level level);

    /** Sections available for the level in the active year. */
    List<String> sectionsFor(Level level);
}
