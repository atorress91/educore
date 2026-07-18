package com.example.educore.certificates.infrastructure.graphql;

import com.example.educore.certificates.application.commands.IssueCertificateCommand;
import com.example.educore.certificates.application.dto.IssuedCertificateResponse;
import com.example.educore.certificates.domain.model.CertificateType;
import com.example.educore.sharedkernel.application.Mediator;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
@RequiredArgsConstructor
public class CertificateMutationController {

    private final Mediator mediator;

    @MutationMapping
    public IssuedCertificateResponse issueCertificate(@Argument CertificateType type, @Argument UUID studentId) {
        return mediator.send(new IssueCertificateCommand(type, studentId));
    }
}
