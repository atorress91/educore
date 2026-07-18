package com.example.educore.certificates.infrastructure.persistence;

import com.example.educore.certificates.domain.model.CertificateType;
import com.example.educore.certificates.domain.model.IssuedCertificate;
import com.example.educore.certificates.domain.model.IssuedCertificateRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaIssuedCertificateRepository
        extends JpaRepository<IssuedCertificate, UUID>, IssuedCertificateRepository {

    Optional<IssuedCertificate> findTopByTypeAndYearOrderByNumberDesc(CertificateType type, int year);

    List<IssuedCertificate> findAllByOrderByIssuedAtDescNumberDesc();
}
