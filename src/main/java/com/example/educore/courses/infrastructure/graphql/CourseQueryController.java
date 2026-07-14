package com.example.educore.courses.infrastructure.graphql;

import com.example.educore.courses.CoursesApi;
import com.example.educore.courses.application.dto.AcademicYearResponse;
import com.example.educore.courses.application.queries.GetAcademicYearsQuery;
import com.example.educore.courses.application.queries.GetActiveAcademicYearQuery;
import com.example.educore.sharedkernel.application.Mediator;
import com.example.educore.sharedkernel.domain.Level;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class CourseQueryController {

    private final Mediator mediator;
    private final CoursesApi coursesApi;

    @QueryMapping
    public AcademicYearResponse activeAcademicYear() {
        return mediator.ask(new GetActiveAcademicYearQuery());
    }

    @QueryMapping
    public List<AcademicYearResponse> academicYears() {
        return mediator.ask(new GetAcademicYearsQuery());
    }

    @QueryMapping
    public List<String> curriculum(@Argument Level level) {
        return coursesApi.subjectsFor(level);
    }

    @QueryMapping
    public List<String> sections(@Argument Level level) {
        return coursesApi.sectionsFor(level);
    }
}
