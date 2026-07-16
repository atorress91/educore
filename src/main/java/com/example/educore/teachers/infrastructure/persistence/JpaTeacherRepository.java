package com.example.educore.teachers.infrastructure.persistence;

import com.example.educore.teachers.domain.model.Teacher;
import com.example.educore.teachers.domain.model.TeacherRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaTeacherRepository extends JpaRepository<Teacher, UUID>, TeacherRepository {

    List<Teacher> findAllByOrderByCodeAsc();

    Optional<Teacher> findByEmail(String email);
}
