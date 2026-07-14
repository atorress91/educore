package com.example.educore.students.domain.model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository {

    Student save(Student student);

    Optional<Student> findById(UUID id);

    List<Student> findAllByOrderByCodeAsc();

    boolean existsByIdentification(String identification);

    long count();
}
