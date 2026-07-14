package com.example.educore.courses.infrastructure.graphql;

import com.example.educore.courses.application.commands.*;
import com.example.educore.courses.application.dto.AcademicYearResponse;
import com.example.educore.sharedkernel.application.Mediator;
import com.example.educore.sharedkernel.domain.Level;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.UUID;

@Controller
@PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
@RequiredArgsConstructor
public class CourseMutationController {

    private final Mediator mediator;

    @MutationMapping
    public AcademicYearResponse createAcademicYear(@Argument int year,
                                                   @Argument String startDate,
                                                   @Argument String endDate) {
        return mediator.send(new CreateAcademicYearCommand(
                year, LocalDate.parse(startDate), LocalDate.parse(endDate)));
    }

    @MutationMapping
    public AcademicYearResponse closeAcademicYear(@Argument UUID id) {
        return mediator.send(new CloseAcademicYearCommand(id));
    }

    @MutationMapping
    public AcademicYearResponse addSubjectToLevel(@Argument Level level, @Argument String subject) {
        return mediator.send(new AddSubjectToLevelCommand(level, subject));
    }

    @MutationMapping
    public AcademicYearResponse removeSubjectFromLevel(@Argument Level level, @Argument String subject) {
        return mediator.send(new RemoveSubjectFromLevelCommand(level, subject));
    }

    @MutationMapping
    public AcademicYearResponse addSectionToLevel(@Argument Level level, @Argument String section) {
        return mediator.send(new AddSectionToLevelCommand(level, section));
    }

    @MutationMapping
    public AcademicYearResponse removeSectionFromLevel(@Argument Level level, @Argument String section) {
        return mediator.send(new RemoveSectionFromLevelCommand(level, section));
    }
}
