package com.example.educore.courses.domain.model;

import com.example.educore.sharedkernel.domain.Level;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Configuration of a single level within an academic year: its sections
 * (e.g. "A", "B") and the subjects taught. Subjects are dynamic strings so
 * the school can add/remove them per level from the Courses admin screen.
 */
@Entity
@Table(name = "level_plans")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LevelPlan {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Level level;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "level_plan_sections", joinColumns = @JoinColumn(name = "level_plan_id"))
    @Column(name = "section", nullable = false)
    private List<String> sections = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "level_plan_subjects", joinColumns = @JoinColumn(name = "level_plan_id"))
    @Column(name = "subject", nullable = false)
    private List<String> subjects = new ArrayList<>();

    LevelPlan(Level level, List<String> sections, List<String> subjects) {
        this.id = UUID.randomUUID();
        this.level = level;
        this.sections = new ArrayList<>(sections);
        this.subjects = new ArrayList<>(subjects);
    }

    void addSection(String section) {
        if (!sections.contains(section)) {
            sections.add(section);
        }
    }

    void removeSection(String section) {
        sections.remove(section);
    }

    void addSubject(String subject) {
        if (!subjects.contains(subject)) {
            subjects.add(subject);
        }
    }

    void removeSubject(String subject) {
        subjects.remove(subject);
    }
}
