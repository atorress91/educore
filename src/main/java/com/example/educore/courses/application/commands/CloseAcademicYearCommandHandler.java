package com.example.educore.courses.application.commands;

import com.example.educore.courses.application.dto.AcademicYearResponse;
import com.example.educore.courses.domain.exceptions.CourseException;
import com.example.educore.courses.domain.model.AcademicYear;
import com.example.educore.courses.domain.model.AcademicYearRepository;
import com.example.educore.sharedkernel.application.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CloseAcademicYearCommandHandler
        implements CommandHandler<CloseAcademicYearCommand, AcademicYearResponse> {

    private final AcademicYearRepository academicYearRepository;

    @Override
    public AcademicYearResponse handle(CloseAcademicYearCommand command) {
        AcademicYear academicYear = academicYearRepository.findById(command.academicYearId())
                .orElseThrow(CourseException::notFound);

        academicYear.close();
        academicYearRepository.save(academicYear);
        return AcademicYearResponse.fromAcademicYear(academicYear);
    }
}
