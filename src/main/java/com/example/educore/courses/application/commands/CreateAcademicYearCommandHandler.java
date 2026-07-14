package com.example.educore.courses.application.commands;

import com.example.educore.courses.application.dto.AcademicYearResponse;
import com.example.educore.courses.domain.exceptions.CourseException;
import com.example.educore.courses.domain.model.AcademicYear;
import com.example.educore.courses.domain.model.AcademicYearRepository;
import com.example.educore.courses.domain.model.AcademicYearStatus;
import com.example.educore.courses.domain.model.DefaultCurriculum;
import com.example.educore.sharedkernel.application.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateAcademicYearCommandHandler
        implements CommandHandler<CreateAcademicYearCommand, AcademicYearResponse> {

    private final AcademicYearRepository academicYearRepository;

    @Override
    public AcademicYearResponse handle(CreateAcademicYearCommand command) {
        if (academicYearRepository.existsByYear(command.year())) {
            throw CourseException.yearAlreadyExists(command.year());
        }
        if (academicYearRepository.findFirstByStatusOrderByYearDesc(AcademicYearStatus.ACTIVE).isPresent()) {
            throw CourseException.activeYearExists();
        }

        AcademicYear academicYear = AcademicYear.open(
                command.year(),
                command.startDate(),
                command.endDate(),
                "Nocturna",
                DefaultCurriculum.plans()
        );

        academicYearRepository.save(academicYear);
        return AcademicYearResponse.fromAcademicYear(academicYear);
    }
}
