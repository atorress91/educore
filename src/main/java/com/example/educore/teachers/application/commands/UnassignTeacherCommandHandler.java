package com.example.educore.teachers.application.commands;

import com.example.educore.courses.CoursesApi;
import com.example.educore.sharedkernel.application.CommandHandler;
import com.example.educore.teachers.domain.model.TeacherAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UnassignTeacherCommandHandler implements CommandHandler<UnassignTeacherCommand, Boolean> {

    private final TeacherAssignmentRepository assignmentRepository;
    private final CoursesApi coursesApi;

    @Override
    public Boolean handle(UnassignTeacherCommand command) {
        assignmentRepository
                .findByYearAndLevelAndSectionAndSubject(
                        coursesApi.activeYear(), command.level(), command.section(), command.subject())
                .ifPresent(assignmentRepository::delete);
        return true;
    }
}
