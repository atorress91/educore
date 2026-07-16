package com.example.educore.teachers.application.queries;

import com.example.educore.courses.CoursesApi;
import com.example.educore.sharedkernel.application.QueryHandler;
import com.example.educore.teachers.application.dto.TeacherAssignmentResponse;
import com.example.educore.teachers.domain.model.Teacher;
import com.example.educore.teachers.domain.model.TeacherAssignmentRepository;
import com.example.educore.teachers.domain.model.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetAllAssignmentsQueryHandler
        implements QueryHandler<GetAllAssignmentsQuery, List<TeacherAssignmentResponse>> {

    private final TeacherAssignmentRepository assignmentRepository;
    private final TeacherRepository teacherRepository;
    private final CoursesApi coursesApi;

    @Override
    public List<TeacherAssignmentResponse> handle(GetAllAssignmentsQuery query) {
        Map<UUID, String> names = teacherRepository.findAllByOrderByCodeAsc().stream()
                .collect(Collectors.toMap(Teacher::getId, Teacher::getFullName, (a, b) -> a));

        return assignmentRepository.findByYear(coursesApi.activeYear()).stream()
                .map(a -> new TeacherAssignmentResponse(
                        a.getLevel(), a.getSection(), a.getSubject(),
                        a.getTeacherId(), names.getOrDefault(a.getTeacherId(), "—")))
                .sorted(Comparator
                        .comparingInt((TeacherAssignmentResponse a) -> a.level().number())
                        .thenComparing(TeacherAssignmentResponse::section)
                        .thenComparing(TeacherAssignmentResponse::subject))
                .toList();
    }
}
