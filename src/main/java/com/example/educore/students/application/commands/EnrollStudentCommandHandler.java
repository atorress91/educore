package com.example.educore.students.application.commands;

import com.example.educore.courses.CoursesApi;
import com.example.educore.sharedkernel.application.CommandHandler;
import com.example.educore.students.application.dto.StudentResponse;
import com.example.educore.students.domain.exceptions.StudentException;
import com.example.educore.students.domain.model.Student;
import com.example.educore.students.domain.model.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EnrollStudentCommandHandler implements CommandHandler<EnrollStudentCommand, StudentResponse> {

    private final StudentRepository studentRepository;
    private final CoursesApi coursesApi;

    @Override
    public StudentResponse handle(EnrollStudentCommand command) {
        if (studentRepository.existsByIdentification(command.identification())) {
            throw StudentException.identificationAlreadyExists(command.identification());
        }

        validateAgainstActiveYear(command);

        Student student = Student.enroll(
                nextCode(),
                command.firstName(),
                command.lastName(),
                command.identification(),
                command.phone(),
                coursesApi.activeYear(),
                command.level(),
                command.section(),
                command.province(),
                command.canton(),
                command.district(),
                command.address(),
                command.guardianName(),
                command.guardianPhone(),
                command.subjects()
        );

        studentRepository.save(student);
        return StudentResponse.fromStudent(student);
    }

    private void validateAgainstActiveYear(EnrollStudentCommand command) {
        if (!coursesApi.sectionsFor(command.level()).contains(command.section())) {
            throw StudentException.invalidSection(command.section());
        }

        List<String> subjects = command.subjects();
        if (subjects == null || subjects.isEmpty()) {
            throw StudentException.noSubjectsSelected();
        }

        List<String> offered = coursesApi.subjectsFor(command.level());
        for (String subject : subjects) {
            if (!offered.contains(subject)) {
                throw StudentException.invalidSubject(subject);
            }
        }
    }

    private String nextCode() {
        return String.format("EST%03d", studentRepository.count() + 1);
    }
}
