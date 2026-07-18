package com.example.educore.grades.application.queries;

import com.example.educore.courses.CoursesApi;
import com.example.educore.grades.application.dto.SectionGradeResponse;
import com.example.educore.grades.domain.model.GradeRepository;
import com.example.educore.sharedkernel.application.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetSectionGradesQueryHandler
        implements QueryHandler<GetSectionGradesQuery, List<SectionGradeResponse>> {

    private final GradeRepository gradeRepository;
    private final CoursesApi coursesApi;

    @Override
    public List<SectionGradeResponse> handle(GetSectionGradesQuery query) {
        return gradeRepository
                .findByYearAndLevelAndSection(coursesApi.activeYear(), query.level(), query.section())
                .stream()
                .map(r -> new SectionGradeResponse(
                        r.getSubject(), r.getStudentId(),
                        r.getTrim1(), r.getTrim2(), r.getTrim3(), r.getAmpl1(), r.getAmpl2()))
                .toList();
    }
}
