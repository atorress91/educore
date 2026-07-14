package com.example.educore.students.application.commands;

import com.example.educore.sharedkernel.application.CommandHandler;
import com.example.educore.students.domain.exceptions.StudentException;
import com.example.educore.students.domain.model.Student;
import com.example.educore.students.domain.model.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteStudentCommandHandler implements CommandHandler<DeleteStudentCommand, Boolean> {

    private final StudentRepository studentRepository;

    @Override
    public Boolean handle(DeleteStudentCommand command) {
        Student student = studentRepository.findById(command.studentId())
                .orElseThrow(StudentException::notFound);

        student.softDelete();
        studentRepository.save(student);
        return true;
    }
}
