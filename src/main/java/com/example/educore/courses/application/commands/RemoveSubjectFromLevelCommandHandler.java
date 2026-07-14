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
public class RemoveSubjectFromLevelCommandHandler
        implements CommandHandler<RemoveSubjectFromLevelCommand, AcademicYearResponse> {

    private final AcademicYearRepository academicYearRepository;

    @Override
    public AcademicYearResponse handle(RemoveSubjectFromLevelCommand command) {
        AcademicYear activeYear = academicYearRepository
                .findFirstByStatusOrderByYearDesc(AcademicYearStatus.ACTIVE)
                .orElseThrow(CourseException::noActiveYear);

        activeYear.removeSubject(command.level(), command.subject());
        academicYearRepository.save(activeYear);
        return AcademicYearResponse.fromAcademicYear(activeYear);
    }
}
