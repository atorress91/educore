package com.example.educore.students.domain.model;

import com.example.educore.sharedkernel.domain.AggregateRoot;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "students")
@SQLRestriction("deleted = false")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student extends AggregateRoot {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String identification;

    @Column(nullable = false)
    private String phone;

    @Column
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Level level;

    @Column(nullable = false)
    private String section;

    @Column(nullable = false)
    private String province;

    @Column(nullable = false)
    private String canton;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String guardianName;

    @Column(nullable = false)
    private String guardianPhone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudentStatus status;

    @Column(nullable = false)
    private LocalDate enrolledAt;

    @Column
    private String imageUrl;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "student_subjects", joinColumns = @JoinColumn(name = "student_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "subject", nullable = false)
    private List<Subject> subjects;

    private Student(String code, String firstName, String lastName, String identification, String phone,
                    Level level, String section, String province, String canton, String district, String address,
                    String guardianName, String guardianPhone) {
        this.id = UUID.randomUUID();
        this.code = code;
        this.firstName = firstName;
        this.lastName = lastName;
        this.identification = identification;
        this.phone = phone;
        this.level = level;
        this.section = section;
        this.province = province;
        this.canton = canton;
        this.district = district;
        this.address = address;
        this.guardianName = guardianName;
        this.guardianPhone = guardianPhone;
        this.status = StudentStatus.ACTIVE;
        this.enrolledAt = LocalDate.now();
        this.subjects = Curriculum.forLevel(level);
    }

    /** Enrolls a new student: active status, today's date, subjects from the level plan. */
    public static Student enroll(String code, String firstName, String lastName, String identification, String phone,
                                 Level level, String section, String province, String canton, String district,
                                 String address, String guardianName, String guardianPhone) {
        return new Student(code, firstName, lastName, identification, phone, level, section,
                province, canton, district, address, guardianName, guardianPhone);
    }
}
