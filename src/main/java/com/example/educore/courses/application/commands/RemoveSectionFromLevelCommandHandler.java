package com.example.educore.courses.application.commands;

import com.example.educore.courses.application.dto.AcademicYearResponse;
import com.example.educore.courses.domain.exceptions.CourseException;
import com.example.educore.courses.domain.model.AcademicYear;
import com.example.educore.courses.domain.model.AcademicYearRepository;
import com.example.educore.courses.domain.model.AcademicYearStatus;
import com.example.educore.sharedkernel.application.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RemoveSectionFromLevelCommandHandler
        implements CommandHandler<RemoveSectionFromLevelCommand, AcademicYearResponse> {

    private final AcademicYearRepository academicYearRepository;

    @Override
    public AcademicYearResponse handle(RemoveSectionFromLevelCommand command) {
        AcademicYear activeYear = academicYearRepository
                .findFirstByStatusOrderByYearDesc(AcademicYearStatus.ACTIVE)
                .orElseThrow(CourseException::noActiveYear);

        activeYear.removeSection(command.level(), command.section());
        academicYearRepository.save(activeYear);
        return AcademicYearResponse.fromAcademicYear(activeYear);
    }
}
