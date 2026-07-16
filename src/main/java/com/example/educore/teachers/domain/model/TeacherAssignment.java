package com.example.educore.teachers.domain.model;

import com.example.educore.sharedkernel.domain.Level;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Assigns a teacher to a specific (level, section, subject) cell of the active
 * academic year — the "Asignar Profesores" matrix. Uniquely identified by the
 * year + level + section + subject; reassigning just swaps the teacher.
 */
@Entity
@Table(name = "teacher_assignments", uniqueConstraints = @UniqueConstraint(
        name = "uk_assignment_slot",
        columnNames = {"year_number", "level", "section", "subject"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeacherAssignment {

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
    private UUID teacherId;

    public TeacherAssignment(int year, Level level, String section, String subject, UUID teacherId) {
        this.id = UUID.randomUUID();
        this.year = year;
        this.level = level;
        this.section = section;
        this.subject = subject;
        this.teacherId = teacherId;
    }

    public void reassign(UUID teacherId) {
        this.teacherId = teacherId;
    }
}
