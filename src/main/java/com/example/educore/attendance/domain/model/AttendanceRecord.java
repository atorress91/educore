package com.example.educore.attendance.domain.model;

import com.example.educore.sharedkernel.domain.Level;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

/**
 * A student's attendance status for a specific class slot on a date:
 * (date, level, section, subject, student). Uniquely identified by that slot;
 * re-marking just updates the status.
 */
@Entity
@Table(name = "attendance_records", uniqueConstraints = @UniqueConstraint(
        name = "uk_attendance_slot",
        columnNames = {"record_date", "level", "section", "subject", "studentId"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttendanceRecord {

    @Id
    private UUID id;

    @Column(name = "record_date", nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Level level;

    @Column(nullable = false)
    private String section;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private UUID studentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceStatus status;

    public AttendanceRecord(LocalDate date, Level level, String section, String subject,
                            UUID studentId, AttendanceStatus status) {
        this.id = UUID.randomUUID();
        this.date = date;
        this.level = level;
        this.section = section;
        this.subject = subject;
        this.studentId = studentId;
        this.status = status;
    }

    public void changeStatus(AttendanceStatus status) {
        this.status = status;
    }
}
