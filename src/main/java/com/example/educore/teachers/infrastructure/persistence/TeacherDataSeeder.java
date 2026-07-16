package com.example.educore.teachers.infrastructure.persistence;

import com.example.educore.teachers.domain.model.Teacher;
import com.example.educore.teachers.domain.model.TeacherRepository;
import com.example.educore.teachers.domain.model.TeacherStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Seeds a few sample teachers on startup so the management view is not empty
 * against the in-memory H2 database. Idempotent: runs only when empty. Subjects
 * mirror the default plan of the active year (see the courses module).
 */
@Component
@Order(2)
@RequiredArgsConstructor
class TeacherDataSeeder implements CommandLineRunner {

    private final TeacherRepository teacherRepository;

    @Override
    public void run(String... args) {
        if (teacherRepository.count() > 0) {
            return;
        }

        // Carlos's email matches the seeded TEACHER login so the attendance view
        // can map the logged-in account to this teacher and load his groups.
        hire("Dr. Carlos Ruiz Hernández", "docente@liceojjjn.edu.cr", "+506 8777-1234",
                "Estudios Sociales", LocalDate.of(2020, 3, 14));
        hire("Lic. Patricia Mora Jiménez", "patricia.mora@colegio.edu", "+506 8777-5678",
                "Matemáticas", LocalDate.of(2019, 8, 19));
        hire("M.Sc. Roberto Quesada", "roberto.quesada@colegio.edu", "+506 8777-9012",
                "Ciencias", LocalDate.of(2021, 1, 9));
        hire("Lic. Laura Vindas Castro", "laura.vindas@colegio.edu", "+506 8777-3456",
                "Español", LocalDate.of(2022, 1, 31));
    }

    private void hire(String fullName, String email, String phone, String subject, LocalDate hiredAt) {
        String code = String.format("PROF%03d", teacherRepository.count() + 1);
        Teacher teacher = Teacher.hire(code, fullName, email, phone, subject, TeacherStatus.ACTIVE, hiredAt);
        teacherRepository.save(teacher);
    }
}
