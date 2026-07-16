package com.example.educore.teachers.domain.model;

import com.example.educore.sharedkernel.domain.AggregateRoot;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Teacher ("Profesor"): a member of the teaching staff. Holds the full name,
 * contact details, the subject ("Materia") they teach (drawn from the active
 * year's curriculum) and a hire date. Managed from the Teachers admin screen.
 */
@Entity
@Table(name = "teachers")
@SQLRestriction("deleted = false")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Teacher extends AggregateRoot {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String subject;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TeacherStatus status;

    @Column(nullable = false)
    private LocalDate hiredAt;

    @Column
    private String imageUrl;

    private Teacher(String code, String fullName, String email, String phone, String subject,
                    TeacherStatus status, LocalDate hiredAt) {
        this.id = UUID.randomUUID();
        this.code = code;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.subject = subject;
        this.status = status;
        this.hiredAt = hiredAt;
    }

    /** Registers a new teacher teaching the given subject. */
    public static Teacher hire(String code, String fullName, String email, String phone, String subject,
                               TeacherStatus status, LocalDate hiredAt) {
        return new Teacher(code, fullName, email, phone, subject, status, hiredAt);
    }

    /** Updates the editable details of the teacher. */
    public void updateDetails(String fullName, String email, String phone, String subject,
                              LocalDate hiredAt, TeacherStatus status) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.subject = subject;
        this.hiredAt = hiredAt;
        this.status = status;
    }
}
