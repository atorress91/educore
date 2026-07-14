package com.example.educore.students.application.dto;

import com.example.educore.sharedkernel.domain.Level;
import com.example.educore.students.domain.model.Student;
import com.example.educore.students.domain.model.StudentStatus;

import java.util.List;
import java.util.UUID;

public record StudentResponse(
        UUID id,
        String code,
        String firstName,
        String lastName,
        String identification,
        String phone,
        String email,
        int academicYear,
        Level level,
        String section,
        String province,
        String canton,
        String district,
        String address,
        String guardianName,
        String guardianPhone,
        StudentStatus status,
        String enrolledAt,
        List<String> subjects,
        String imageUrl
) {
    public static StudentResponse fromStudent(Student student) {
        return new StudentResponse(
                student.getId(),
                student.getCode(),
                student.getFirstName(),
                student.getLastName(),
                student.getIdentification(),
                student.getPhone(),
                student.getEmail(),
                student.getAcademicYear(),
                student.getLevel(),
                student.getSection(),
                student.getProvince(),
                student.getCanton(),
                student.getDistrict(),
                student.getAddress(),
                student.getGuardianName(),
                student.getGuardianPhone(),
                student.getStatus(),
                student.getEnrolledAt().toString(),
                student.getSubjects(),
                student.getImageUrl()
        );
    }
}
