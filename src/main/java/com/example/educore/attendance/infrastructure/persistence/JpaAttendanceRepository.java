package com.example.educore.attendance.infrastructure.persistence;

import com.example.educore.attendance.domain.model.AttendanceRecord;
import com.example.educore.attendance.domain.model.AttendanceRepository;
import com.example.educore.sharedkernel.domain.Level;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaAttendanceRepository extends JpaRepository<AttendanceRecord, UUID>, AttendanceRepository {

    List<AttendanceRecord> findByDateAndLevelAndSectionAndSubject(
            LocalDate date, Level level, String section, String subject);

    Optional<AttendanceRecord> findByDateAndLevelAndSectionAndSubjectAndStudentId(
            LocalDate date, Level level, String section, String subject, UUID studentId);

    List<AttendanceRecord> findByDate(LocalDate date);
}
