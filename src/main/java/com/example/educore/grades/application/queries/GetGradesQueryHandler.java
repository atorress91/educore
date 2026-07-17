package com.example.educore.grades.application.queries;

import com.example.educore.courses.CoursesApi;
import com.example.educore.grades.application.dto.GradeEntryResponse;
import com.example.educore.grades.domain.model.GradeRepository;
import com.example.educore.sharedkernel.application.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetGradesQueryHandler implements QueryHandler<GetGradesQuery, List<GradeEntryResponse>> {

    private final GradeRepository gradeRepository;
    private final CoursesApi coursesApi;

    @Override
    public List<GradeEntryResponse> handle(GetGradesQuery query) {
        return gradeRepository
                .findByYearAndLevelAndSectionAndSubject(
                        coursesApi.activeYear(), query.level(), query.section(), query.subject())
                .stream()
                .map(r -> new GradeEntryResponse(
                        r.getStudentId(), r.getTrim1(), r.getTrim2(), r.getTrim3(), r.getAmpl1(), r.getAmpl2()))
                .toList();
    }
}
