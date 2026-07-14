package com.example.educore.students.application.commands;

import com.example.educore.sharedkernel.application.CommandHandler;
import com.example.educore.students.application.dto.StudentResponse;
import com.example.educore.students.domain.exceptions.StudentException;
import com.example.educore.students.domain.model.Student;
import com.example.educore.students.domain.model.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EnrollStudentCommandHandler implements CommandHandler<EnrollStudentCommand, StudentResponse> {

    private final StudentRepository studentRepository;

    @Override
    public StudentResponse handle(EnrollStudentCommand command) {
        if (studentRepository.existsByIdentification(command.identification())) {
            throw StudentException.identificationAlreadyExists(command.identification());
        }

        Student student = Student.enroll(
                nextCode(),
                command.firstName(),
                command.lastName(),
                command.identification(),
                command.phone(),
                command.level(),
                command.section(),
                command.province(),
                command.canton(),
                command.district(),
                command.address(),
                command.guardianName(),
                command.guardianPhone()
        );

        studentRepository.save(student);
        return StudentResponse.fromStudent(student);
    }

    private String nextCode() {
        return String.format("EST%03d", studentRepository.count() + 1);
    }
}
