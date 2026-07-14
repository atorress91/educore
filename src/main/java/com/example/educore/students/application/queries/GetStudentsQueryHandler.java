package com.example.educore.students.application.queries;

import com.example.educore.sharedkernel.application.QueryHandler;
import com.example.educore.students.application.dto.StudentResponse;
import com.example.educore.students.domain.model.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetStudentsQueryHandler implements QueryHandler<GetStudentsQuery, List<StudentResponse>> {

    private final StudentRepository studentRepository;

    @Override
    public List<StudentResponse> handle(GetStudentsQuery query) {
        return studentRepository.findAllByOrderByCodeAsc().stream()
                .map(StudentResponse::fromStudent)
                .toList();
    }
}
