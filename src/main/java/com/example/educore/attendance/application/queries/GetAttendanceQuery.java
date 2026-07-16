package com.example.educore.attendance.application.queries;

import com.example.educore.attendance.application.dto.AttendanceEntryResponse;
import com.example.educore.sharedkernel.application.Query;
import com.example.educore.sharedkernel.domain.Level;

import java.time.LocalDate;
import java.util.List;

public record GetAttendanceQuery(
        Level level,
        String section,
        String subject,
        LocalDate date
) implements Query<List<AttendanceEntryResponse>> {}
