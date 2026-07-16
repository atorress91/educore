package com.example.educore.teachers.infrastructure.persistence;

import com.example.educore.sharedkernel.domain.Level;
import com.example.educore.teachers.domain.model.TeacherAssignment;
import com.example.educore.teachers.domain.model.TeacherAssignmentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaTeacherAssignmentRepository
        extends JpaRepository<TeacherAssignment, UUID>, TeacherAssignmentRepository {

    List<TeacherAssignment> findByYearAndLevel(int year, Level level);

    Optional<TeacherAssignment> findByYearAndLevelAndSectionAndSubject(
            int year, Level level, String section, String subject);
}
