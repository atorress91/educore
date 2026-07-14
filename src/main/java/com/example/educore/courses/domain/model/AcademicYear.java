package com.example.educore.courses.domain.model;

import com.example.educore.courses.domain.exceptions.CourseException;
import com.example.educore.sharedkernel.domain.AggregateRoot;
import com.example.educore.sharedkernel.domain.Level;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Academic year ("Curso Lectivo"): the top of the academic structure. Owns a
 * plan per level (sections + subjects). Lifecycle: opens ACTIVE, later CLOSED.
 */
@Entity
@Table(name = "academic_years")
@SQLRestriction("deleted = false")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AcademicYear extends AggregateRoot {

    @Id
    private UUID id;

    @Column(name = "year_number", nullable = false, unique = true)
    private int year;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AcademicYearStatus status;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String modality;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "academic_year_id", nullable = false)
    private List<LevelPlan> levels = new ArrayList<>();

    private AcademicYear(int year, LocalDate startDate, LocalDate endDate, String modality, List<LevelPlan> levels) {
        this.id = UUID.randomUUID();
        this.year = year;
        this.status = AcademicYearStatus.ACTIVE;
        this.startDate = startDate;
        this.endDate = endDate;
        this.modality = modality;
        this.levels = levels;
    }

    /** Opens a new active year with the given per-level plans. */
    public static AcademicYear open(int year, LocalDate startDate, LocalDate endDate, String modality,
                                    List<LevelPlan> levels) {
        return new AcademicYear(year, startDate, endDate, modality, levels);
    }

    public static LevelPlan planFor(Level level, List<String> sections, List<String> subjects) {
        return new LevelPlan(level, sections, subjects);
    }

    public void close() {
        this.status = AcademicYearStatus.CLOSED;
    }

    public void addSubject(Level level, String subject) {
        planOf(level).addSubject(subject);
    }

    public void removeSubject(Level level, String subject) {
        planOf(level).removeSubject(subject);
    }

    public void addSection(Level level, String section) {
        planOf(level).addSection(section);
    }

    public void removeSection(Level level, String section) {
        planOf(level).removeSection(section);
    }

    public LevelPlan planOf(Level level) {
        return levels.stream()
                .filter(plan -> plan.getLevel() == level)
                .findFirst()
                .orElseThrow(() -> CourseException.levelNotConfigured(level.number()));
    }

    public int sectionCount() {
        return levels.stream().mapToInt(plan -> plan.getSections().size()).sum();
    }
}
