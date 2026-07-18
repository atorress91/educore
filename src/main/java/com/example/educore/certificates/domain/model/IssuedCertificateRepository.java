package com.example.educore.certificates.domain.model;

import java.util.List;
import java.util.Optional;

public interface IssuedCertificateRepository {

    IssuedCertificate save(IssuedCertificate certificate);

    /** The highest-numbered certificate of a type in a year, for the next correlative. */
    Optional<IssuedCertificate> findTopByTypeAndYearOrderByNumberDesc(CertificateType type, int year);

    List<IssuedCertificate> findAllByOrderByIssuedAtDescNumberDesc();
}
