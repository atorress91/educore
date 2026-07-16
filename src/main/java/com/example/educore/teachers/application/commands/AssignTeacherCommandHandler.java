package com.example.educore.teachers.application.commands;

import com.example.educore.courses.CoursesApi;
import com.example.educore.sharedkernel.application.CommandHandler;
import com.example.educore.teachers.application.dto.TeacherAssignmentResponse;
import com.example.educore.teachers.domain.exceptions.TeacherException;
import com.example.educore.teachers.domain.model.Teacher;
import com.example.educore.teachers.domain.model.TeacherAssignment;
import com.example.educore.teachers.domain.model.TeacherAssignmentRepository;
import com.example.educore.teachers.domain.model.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AssignTeacherCommandHandler
        implements CommandHandler<AssignTeacherCommand, TeacherAssignmentResponse> {

    private final TeacherAssignmentRepository assignmentRepository;
    private final TeacherRepository teacherRepository;
    private final CoursesApi coursesApi;

    @Override
    public TeacherAssignmentResponse handle(AssignTeacherCommand command) {
        if (!coursesApi.sectionsFor(command.level()).contains(command.section())) {
            throw TeacherException.invalidSection(command.section());
        }
        if (!coursesApi.subjectsFor(command.level()).contains(command.subject())) {
            throw TeacherException.invalidSubject(command.subject());
        }

        Teacher teacher = teacherRepository.findById(command.teacherId())
                .orElseThrow(TeacherException::notFound);

        int year = coursesApi.activeYear();
        TeacherAssignment assignment = assignmentRepository
                .findByYearAndLevelAndSectionAndSubject(year, command.level(), command.section(), command.subject())
                .map(existing -> {
                    existing.reassign(command.teacherId());
                    return existing;
                })
                .orElseGet(() -> new TeacherAssignment(
                        year, command.level(), command.section(), command.subject(), command.teacherId()));

        assignmentRepository.save(assignment);
        return new TeacherAssignmentResponse(
                assignment.getLevel(), assignment.getSection(), assignment.getSubject(),
                assignment.getTeacherId(), teacher.getFullName());
    }
}
