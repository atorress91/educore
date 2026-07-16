package com.example.educore.teachers.domain.model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TeacherRepository {

    Teacher save(Teacher teacher);

    Optional<Teacher> findById(UUID id);

    List<Teacher> findAllByOrderByCodeAsc();

    Optional<Teacher> findByEmail(String email);

    long count();
}
