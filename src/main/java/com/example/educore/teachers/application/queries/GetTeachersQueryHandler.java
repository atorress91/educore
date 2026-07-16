package com.example.educore.teachers.application.queries;

import com.example.educore.sharedkernel.application.QueryHandler;
import com.example.educore.teachers.application.dto.TeacherResponse;
import com.example.educore.teachers.domain.model.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetTeachersQueryHandler implements QueryHandler<GetTeachersQuery, List<TeacherResponse>> {

    private final TeacherRepository teacherRepository;

    @Override
    public List<TeacherResponse> handle(GetTeachersQuery query) {
        return teacherRepository.findAllByOrderByCodeAsc().stream()
                .map(TeacherResponse::fromTeacher)
                .toList();
    }
}
