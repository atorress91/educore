package com.example.educore.grades.domain.model;

import com.example.educore.sharedkernel.domain.Level;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * A student's grades for a class slot in an academic year:
 * (year, level, section, subject, student). Holds the three trimester scores
 * plus two optional recovery ("ampliación") scores; each is 0-100 or null when
 * not yet entered. The final total and pass/fail status are derived, not stored.
 */
@Entity
@Table(name = "grade_records", uniqueConstraints = @UniqueConstraint(
        name = "uk_grade_slot",
        columnNames = {"year_number", "level", "section", "subject", "studentId"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GradeRecord {

    @Id
    private UUID id;

    @Column(name = "year_number", nullable = false)
    private int year;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Level level;

    @Column(nullable = false)
    private String section;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private UUID studentId;

    private Integer trim1;
    private Integer trim2;
    private Integer trim3;
    private Integer ampl1;
    private Integer ampl2;

    public GradeRecord(int year, Level level, String section, String subject, UUID studentId) {
        this.id = UUID.randomUUID();
        this.year = year;
        this.level = level;
        this.section = section;
        this.subject = subject;
        this.studentId = studentId;
    }

    public void updateScores(Integer trim1, Integer trim2, Integer trim3, Integer ampl1, Integer ampl2) {
        this.trim1 = trim1;
        this.trim2 = trim2;
        this.trim3 = trim3;
        this.ampl1 = ampl1;
        this.ampl2 = ampl2;
    }
}
