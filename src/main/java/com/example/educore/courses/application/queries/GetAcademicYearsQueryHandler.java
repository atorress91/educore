package com.example.educore.courses.application.queries;

import com.example.educore.courses.application.dto.AcademicYearResponse;
import com.example.educore.courses.domain.model.AcademicYearRepository;
import com.example.educore.sharedkernel.application.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetAcademicYearsQueryHandler
        implements QueryHandler<GetAcademicYearsQuery, List<AcademicYearResponse>> {

    private final AcademicYearRepository academicYearRepository;

    @Override
    public List<AcademicYearResponse> handle(GetAcademicYearsQuery query) {
        return academicYearRepository.findAllByOrderByYearDesc().stream()
                .map(AcademicYearResponse::fromAcademicYear)
                .toList();
    }
}
