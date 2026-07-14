package com.example.educore.courses.application.queries;

import com.example.educore.courses.application.dto.AcademicYearResponse;
import com.example.educore.courses.domain.model.AcademicYearRepository;
import com.example.educore.courses.domain.model.AcademicYearStatus;
import com.example.educore.sharedkernel.application.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetActiveAcademicYearQueryHandler
        implements QueryHandler<GetActiveAcademicYearQuery, AcademicYearResponse> {

    private final AcademicYearRepository academicYearRepository;

    @Override
    public AcademicYearResponse handle(GetActiveAcademicYearQuery query) {
        return academicYearRepository.findFirstByStatusOrderByYearDesc(AcademicYearStatus.ACTIVE)
                .map(AcademicYearResponse::fromAcademicYear)
                .orElse(null);
    }
}
