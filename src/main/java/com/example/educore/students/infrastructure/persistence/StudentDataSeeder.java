package com.example.educore.students.infrastructure.persistence;

import com.example.educore.sharedkernel.domain.Level;
import com.example.educore.students.domain.model.Student;
import com.example.educore.students.domain.model.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Seeds a few sample students on startup so the management view is not empty
 * against the in-memory H2 database. Idempotent: runs only when empty. Subjects
 * mirror the default plan for each level (see the courses module).
 */
@Component
@Order(3)
@RequiredArgsConstructor
class StudentDataSeeder implements CommandLineRunner {

    private static final int ACTIVE_YEAR = 2026;

    private static final List<String> THIRD_CYCLE = List.of(
            "Estudios Sociales", "Ciencias", "Matemáticas", "Inglés", "Español", "Ética", "Contabilidad", "Cívica");

    private static final List<String> DIVERSIFIED = List.of(
            "Estudios Sociales", "Cívica", "Biología", "Matemáticas", "Inglés", "Español");

    private final StudentRepository studentRepository;

    @Override
    public void run(String... args) {
        if (studentRepository.count() > 0) {
            return;
        }

        enroll("María", "González Pérez", "1-1111-1111", "+506 8888-1234", Level.EIGHT, "A",
                "San José", "San José", "Carmen", "100 m norte del parque", "Rosa Pérez", "+506 8811-1111", THIRD_CYCLE);
        enroll("Carlos", "Ramírez Castro", "2-2222-2222", "+506 8888-5678", Level.NINE, "A",
                "Alajuela", "Alajuela", "Alajuela", "Frente a la escuela", "Luis Ramírez", "+506 8822-2222", THIRD_CYCLE);
        enroll("Ana Sofía", "Vargas", "3-3333-3333", "+506 8888-9012", Level.SEVEN, "A",
                "Cartago", "Cartago", "Oriental", "Contiguo a la iglesia", "Marta Vargas", "+506 8833-3333", THIRD_CYCLE);
        enroll("José Luis", "Mora", "4-4444-4444", "+506 8888-3456", Level.TEN, "A",
                "Heredia", "Heredia", "Heredia", "200 m sur del super", "Carmen Mora", "+506 8844-4444", DIVERSIFIED);
    }

    private void enroll(String firstName, String lastName, String identification, String phone, Level level,
                        String section, String province, String canton, String district, String address,
                        String guardianName, String guardianPhone, List<String> subjects) {
        String code = String.format("EST%03d", studentRepository.count() + 1);
        Student student = Student.enroll(code, firstName, lastName, identification, phone, ACTIVE_YEAR, level,
                section, province, canton, district, address, guardianName, guardianPhone, subjects);
        studentRepository.save(student);
    }
}
