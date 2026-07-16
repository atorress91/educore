package com.example.educore.teachers.application.commands;

import com.example.educore.courses.CoursesApi;
import com.example.educore.sharedkernel.application.CommandHandler;
import com.example.educore.teachers.application.dto.TeacherResponse;
import com.example.educore.teachers.domain.exceptions.TeacherException;
import com.example.educore.teachers.domain.model.Teacher;
import com.example.educore.teachers.domain.model.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateTeacherCommandHandler implements CommandHandler<CreateTeacherCommand, TeacherResponse> {

    private final TeacherRepository teacherRepository;
    private final CoursesApi coursesApi;

    @Override
    public TeacherResponse handle(CreateTeacherCommand command) {
        if (!coursesApi.allSubjects().contains(command.subject())) {
            throw TeacherException.invalidSubject(command.subject());
        }

        Teacher teacher = Teacher.hire(
                nextCode(),
                command.fullName(),
                command.email(),
                command.phone(),
                command.subject(),
                command.status(),
                command.hiredAt()
        );

        teacherRepository.save(teacher);
        return TeacherResponse.fromTeacher(teacher);
    }

    private String nextCode() {
        return String.format("PROF%03d", teacherRepository.count() + 1);
    }
}
