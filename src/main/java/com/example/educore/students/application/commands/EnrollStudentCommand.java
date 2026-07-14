package com.example.educore.students.application.commands;

import com.example.educore.sharedkernel.application.Command;
import com.example.educore.sharedkernel.domain.Level;
import com.example.educore.students.application.dto.StudentResponse;

import java.util.List;

public record EnrollStudentCommand(
        String firstName,
        String lastName,
        String identification,
        String phone,
        Level level,
        String section,
        String province,
        String canton,
        String district,
        String address,
        String guardianName,
        String guardianPhone,
        List<String> subjects
) implements Command<StudentResponse> {}
