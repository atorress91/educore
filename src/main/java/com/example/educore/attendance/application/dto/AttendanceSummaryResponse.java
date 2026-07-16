package com.example.educore.attendance.application.dto;

import com.example.educore.sharedkernel.domain.Level;

/** Attendance counts for a class slot (level+section+subject) on a date. */
public record AttendanceSummaryResponse(
        Level level,
        String section,
        String subject,
        int present,
        int absent,
        int late,
        int justified
) {}
