package com.example.educore.teachers.application.dto;

import com.example.educore.teachers.domain.model.Teacher;
import com.example.educore.teachers.domain.model.TeacherStatus;

import java.util.UUID;

public record TeacherResponse(
        UUID id,
        String code,
        String fullName,
        String email,
        String phone,
        String subject,
        TeacherStatus status,
        String hiredAt,
        String imageUrl
) {
    public static TeacherResponse fromTeacher(Teacher teacher) {
        return new TeacherResponse(
                teacher.getId(),
                teacher.getCode(),
                teacher.getFullName(),
                teacher.getEmail(),
                teacher.getPhone(),
                teacher.getSubject(),
                teacher.getStatus(),
                teacher.getHiredAt().toString(),
                teacher.getImageUrl()
        );
    }
}
