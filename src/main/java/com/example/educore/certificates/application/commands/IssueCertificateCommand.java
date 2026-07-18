package com.example.educore.certificates.application.commands;

import com.example.educore.certificates.application.dto.IssuedCertificateResponse;
import com.example.educore.certificates.domain.model.CertificateType;
import com.example.educore.sharedkernel.application.Command;

import java.util.UUID;

public record IssueCertificateCommand(
        CertificateType type,
        UUID studentId
) implements Command<IssuedCertificateResponse> {}
