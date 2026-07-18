package com.example.educore.certificates.application.commands;

import com.example.educore.certificates.application.dto.IssuedCertificateResponse;
import com.example.educore.certificates.domain.model.IssuedCertificate;
import com.example.educore.certificates.domain.model.IssuedCertificateRepository;
import com.example.educore.courses.CoursesApi;
import com.example.educore.sharedkernel.application.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class IssueCertificateCommandHandler
        implements CommandHandler<IssueCertificateCommand, IssuedCertificateResponse> {

    private final IssuedCertificateRepository certificateRepository;
    private final CoursesApi coursesApi;

    @Override
    public IssuedCertificateResponse handle(IssueCertificateCommand command) {
        int year = coursesApi.activeYear();
        int next = certificateRepository.findTopByTypeAndYearOrderByNumberDesc(command.type(), year)
                .map(last -> last.getNumber() + 1)
                .orElse(1);
        IssuedCertificate saved = certificateRepository.save(
                new IssuedCertificate(command.type(), command.studentId(), next, year));
        return new IssuedCertificateResponse(
                saved.getId(), saved.getType(), saved.getStudentId(),
                saved.getNumber(), saved.getYear(), saved.getIssuedAt().toString());
    }
}
