package com.example.educore.certificates.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

/**
 * A record of a certificate emitted for a student. Each carries a correlative
 * number that is consecutive per type and academic year (e.g. GRADES 041-2026),
 * matching the official numbering on the printed document.
 */
@Entity
@Table(name = "issued_certificates")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IssuedCertificate {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CertificateType type;

    @Column(nullable = false)
    private UUID studentId;

    @Column(nullable = false)
    private int number;

    @Column(name = "year_number", nullable = false)
    private int year;

    @Column(nullable = false)
    private LocalDate issuedAt;

    public IssuedCertificate(CertificateType type, UUID studentId, int number, int year) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.studentId = studentId;
        this.number = number;
        this.year = year;
        this.issuedAt = LocalDate.now();
    }
}
