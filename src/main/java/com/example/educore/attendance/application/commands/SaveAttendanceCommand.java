package com.example.educore.attendance.application.commands;

import com.example.educore.attendance.domain.model.AttendanceStatus;
import com.example.educore.sharedkernel.application.Command;
import com.example.educore.sharedkernel.domain.Level;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record SaveAttendanceCommand(
        Level level,
        String section,
        String subject,
        LocalDate date,
        List<Entry> entries
) implements Command<Boolean> {

    public record Entry(UUID studentId, AttendanceStatus status) {}
}
