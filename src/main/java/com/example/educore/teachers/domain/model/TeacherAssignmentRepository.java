package com.example.educore.teachers.domain.model;

import com.example.educore.sharedkernel.domain.Level;

import java.util.List;
import java.util.Optional;

public interface TeacherAssignmentRepository {

    TeacherAssignment save(TeacherAssignment assignment);

    List<TeacherAssignment> findByYearAndLevel(int year, Level level);

    List<TeacherAssignment> findByYear(int year);

    List<TeacherAssignment> findByYearAndTeacherId(int year, java.util.UUID teacherId);

    Optional<TeacherAssignment> findByYearAndLevelAndSectionAndSubject(
            int year, Level level, String section, String subject);

    void delete(TeacherAssignment assignment);
}
