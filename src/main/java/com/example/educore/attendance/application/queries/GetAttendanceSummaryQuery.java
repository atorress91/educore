package com.example.educore.attendance.application.queries;

import com.example.educore.attendance.application.dto.AttendanceSummaryResponse;
import com.example.educore.sharedkernel.application.Query;

import java.time.LocalDate;
import java.util.List;

public record GetAttendanceSummaryQuery(LocalDate date) implements Query<List<AttendanceSummaryResponse>> {}
