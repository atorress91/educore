package com.example.educore.teachers.application.queries;

import com.example.educore.auth.AuthApi;
import com.example.educore.courses.CoursesApi;
import com.example.educore.sharedkernel.application.QueryHandler;
import com.example.educore.sharedkernel.domain.Level;
import com.example.educore.teachers.application.dto.TeacherGroupResponse;
import com.example.educore.teachers.domain.model.TeacherAssignmentRepository;
import com.example.educore.teachers.domain.model.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

/**
 * Resolves the groups (subject + level + section) taught by the logged-in
 * teacher, by mapping the authenticated account to a Teacher via email and
 * reading its assignments for the active year. Returns empty if the account is
 * not linked to a teacher.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetMyGroupsQueryHandler implements QueryHandler<GetMyGroupsQuery, List<TeacherGroupResponse>> {

    private final TeacherRepository teacherRepository;
    private final TeacherAssignmentRepository assignmentRepository;
    private final AuthApi authApi;
    private final CoursesApi coursesApi;

    @Override
    public List<TeacherGroupResponse> handle(GetMyGroupsQuery query) {
        return authApi.emailOf(query.userId())
                .flatMap(teacherRepository::findByEmail)
                .map(teacher -> assignmentRepository
                        .findByYearAndTeacherId(coursesApi.activeYear(), teacher.getId()).stream()
                        .map(a -> new TeacherGroupResponse(a.getLevel(), a.getSection(), a.getSubject()))
                        .sorted(Comparator
                                .comparingInt((TeacherGroupResponse g) -> g.level().number())
                                .thenComparing(TeacherGroupResponse::section)
                                .thenComparing(TeacherGroupResponse::subject))
                        .toList())
                .orElseGet(List::of);
    }
}
