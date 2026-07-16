package com.example.educore.attendance.domain.model;

import com.example.educore.sharedkernel.domain.Level;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AttendanceRepository {

    AttendanceRecord save(AttendanceRecord record);

    List<AttendanceRecord> findByDateAndLevelAndSectionAndSubject(
            LocalDate date, Level level, String section, String subject);

    Optional<AttendanceRecord> findByDateAndLevelAndSectionAndSubjectAndStudentId(
            LocalDate date, Level level, String section, String subject, UUID studentId);

    List<AttendanceRecord> findByDate(LocalDate date);
}
