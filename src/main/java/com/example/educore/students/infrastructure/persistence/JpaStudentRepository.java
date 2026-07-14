package com.example.educore.students.infrastructure.persistence;

import com.example.educore.students.domain.model.Student;
import com.example.educore.students.domain.model.StudentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaStudentRepository extends JpaRepository<Student, UUID>, StudentRepository {

    List<Student> findAllByOrderByCodeAsc();

    boolean existsByIdentification(String identification);
}
