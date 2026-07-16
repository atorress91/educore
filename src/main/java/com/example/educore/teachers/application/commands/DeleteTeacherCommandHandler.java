package com.example.educore.teachers.application.commands;

import com.example.educore.sharedkernel.application.CommandHandler;
import com.example.educore.teachers.domain.exceptions.TeacherException;
import com.example.educore.teachers.domain.model.Teacher;
import com.example.educore.teachers.domain.model.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteTeacherCommandHandler implements CommandHandler<DeleteTeacherCommand, Boolean> {

    private final TeacherRepository teacherRepository;

    @Override
    public Boolean handle(DeleteTeacherCommand command) {
        Teacher teacher = teacherRepository.findById(command.teacherId())
                .orElseThrow(TeacherException::notFound);

        teacher.softDelete();
        teacherRepository.save(teacher);
        return true;
    }
}
