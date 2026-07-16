package com.example.educore.teachers.infrastructure.persistence;

import com.example.educore.courses.CoursesApi;
import com.example.educore.sharedkernel.domain.Level;
import com.example.educore.teachers.domain.model.Teacher;
import com.example.educore.teachers.domain.model.TeacherAssignment;
import com.example.educore.teachers.domain.model.TeacherAssignmentRepository;
import com.example.educore.teachers.domain.model.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Seeds a few teacher-to-(level, section, subject) assignments for the active
 * year so the attendance view has data (the seeded teacher account maps to
 * Carlos, who gets sections 7-A, 7-B and 8-A). Idempotent. Runs after teachers
 * and courses are seeded.
 */
@Component
@Order(4)
@RequiredArgsConstructor
class TeacherAssignmentSeeder implements CommandLineRunner {

    private final TeacherAssignmentRepository assignmentRepository;
    private final TeacherRepository teacherRepository;
    private final CoursesApi coursesApi;

    @Override
    public void run(String... args) {
        int year = coursesApi.activeYear();
        if (!assignmentRepository.findByYear(year).isEmpty()) {
            return;
        }

        Map<String, Teacher> bySubject = teacherRepository.findAllByOrderByCodeAsc().stream()
                .collect(Collectors.toMap(Teacher::getSubject, Function.identity(), (a, b) -> a));

        // Carlos (Estudios Sociales): 7-A, 7-B, 8-A -> the demo teacher's groups.
        assign(year, Level.SEVEN, "A", "Estudios Sociales", bySubject);
        assign(year, Level.SEVEN, "B", "Estudios Sociales", bySubject);
        assign(year, Level.EIGHT, "A", "Estudios Sociales", bySubject);
        // A couple more so the admin overview and matrix are not empty.
        assign(year, Level.SEVEN, "A", "Matemáticas", bySubject);
        assign(year, Level.SEVEN, "A", "Ciencias", bySubject);
        assign(year, Level.SEVEN, "A", "Español", bySubject);
    }

    private void assign(int year, Level level, String section, String subject, Map<String, Teacher> bySubject) {
        Teacher teacher = bySubject.get(subject);
        if (teacher == null || !coursesApi.sectionsFor(level).contains(section)
                || !coursesApi.subjectsFor(level).contains(subject)) {
            return;
        }
        assignmentRepository.save(new TeacherAssignment(year, level, section, subject, teacher.getId()));
    }
}
