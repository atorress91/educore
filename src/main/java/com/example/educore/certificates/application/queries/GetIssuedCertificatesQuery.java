package com.example.educore.certificates.application.queries;

import com.example.educore.certificates.application.dto.IssuedCertificateResponse;
import com.example.educore.sharedkernel.application.Query;

import java.util.List;

public record GetIssuedCertificatesQuery() implements Query<List<IssuedCertificateResponse>> {}
