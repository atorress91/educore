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

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetAssignmentsQueryHandler
        implements QueryHandler<GetAssignmentsQuery, List<TeacherAssignmentResponse>> {

    private final TeacherAssignmentRepository assignmentRepository;
    private final TeacherRepository teacherRepository;
    private final CoursesApi coursesApi;

    @Override
    public List<TeacherAssignmentResponse> handle(GetAssignmentsQuery query) {
        Map<UUID, String> names = teacherRepository.findAllByOrderByCodeAsc().stream()
                .collect(Collectors.toMap(Teacher::getId, Teacher::getFullName, (a, b) -> a));

        return assignmentRepository.findByYearAndLevel(coursesApi.activeYear(), query.level()).stream()
                .map(a -> new TeacherAssignmentResponse(
                        a.getLevel(),
                        a.getSection(),
                        a.getSubject(),
                        a.getTeacherId(),
                        names.getOrDefault(a.getTeacherId(), "—")))
                .sorted((a, b) -> {
                    int bySubject = a.subject().compareTo(b.subject());
                    return bySubject != 0 ? bySubject : a.section().compareTo(b.section());
                })
                .toList();
    }
}
