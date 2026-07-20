package com.example.educore.grades.infrastructure.graphql;

import com.example.educore.grades.application.dto.GradeEntryResponse;
import com.example.educore.grades.application.dto.SectionGradeResponse;
import com.example.educore.grades.application.queries.GetGradesQuery;
import com.example.educore.grades.application.queries.GetSectionGradesQuery;
import com.example.educore.sharedkernel.application.Mediator;
import com.example.educore.sharedkernel.domain.Level;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@PreAuthorize("@perm.can('GRADES','READ')")
@RequiredArgsConstructor
public class GradeQueryController {

    private final Mediator mediator;

    @QueryMapping
    public List<GradeEntryResponse> grades(@Argument Level level, @Argument String section,
                                           @Argument String subject) {
        return mediator.ask(new GetGradesQuery(level, section, subject));
    }

    @QueryMapping
    public List<SectionGradeResponse> sectionGrades(@Argument Level level, @Argument String section) {
        return mediator.ask(new GetSectionGradesQuery(level, section));
    }
}
