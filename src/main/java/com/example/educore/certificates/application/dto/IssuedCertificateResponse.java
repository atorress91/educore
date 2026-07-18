package com.example.educore.certificates.application.dto;

import com.example.educore.certificates.domain.model.CertificateType;

import java.util.UUID;

public record IssuedCertificateResponse(
        UUID id,
        CertificateType type,
        UUID studentId,
        int number,
        int year,
        String issuedAt
) {}
