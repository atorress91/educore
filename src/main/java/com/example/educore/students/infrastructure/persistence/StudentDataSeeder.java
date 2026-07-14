package com.example.educore.students.infrastructure.persistence;

import com.example.educore.students.domain.model.Level;
import com.example.educore.students.domain.model.Student;
import com.example.educore.students.domain.model.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Seeds a few sample students on startup so the management view is not empty
 * against the in-memory H2 database. Idempotent: runs only when empty.
 */
@Component
@Order(2)
@RequiredArgsConstructor
class StudentDataSeeder implements CommandLineRunner {

    private final StudentRepository studentRepository;

    @Override
    public void run(String... args) {
        if (studentRepository.count() > 0) {
            return;
        }

        enroll("María", "González Pérez", "1-1111-1111", "+506 8888-1234", Level.EIGHT, "A",
                "San José", "San José", "Carmen", "100 m norte del parque", "Rosa Pérez", "+506 8811-1111");
        enroll("Carlos", "Ramírez Castro", "2-2222-2222", "+506 8888-5678", Level.NINE, "B",
                "Alajuela", "Alajuela", "Alajuela", "Frente a la escuela", "Luis Ramírez", "+506 8822-2222");
        enroll("Ana Sofía", "Vargas", "3-3333-3333", "+506 8888-9012", Level.SEVEN, "A",
                "Cartago", "Cartago", "Oriental", "Contiguo a la iglesia", "Marta Vargas", "+506 8833-3333");
        enroll("José Luis", "Mora", "4-4444-4444", "+506 8888-3456", Level.TEN, "A",
                "Heredia", "Heredia", "Heredia", "200 m sur del super", "Carmen Mora", "+506 8844-4444");
    }

    private void enroll(String firstName, String lastName, String identification, String phone, Level level,
                        String section, String province, String canton, String district, String address,
                        String guardianName, String guardianPhone) {
        String code = String.format("EST%03d", studentRepository.count() + 1);
        Student student = Student.enroll(code, firstName, lastName, identification, phone, level, section,
                province, canton, district, address, guardianName, guardianPhone);
        studentRepository.save(student);
    }
}
