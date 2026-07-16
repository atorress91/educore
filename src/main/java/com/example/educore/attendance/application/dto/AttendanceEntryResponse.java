package com.example.educore.attendance.application.dto;

import com.example.educore.attendance.domain.model.AttendanceStatus;

import java.util.UUID;

public record AttendanceEntryResponse(UUID studentId, AttendanceStatus status) {}
