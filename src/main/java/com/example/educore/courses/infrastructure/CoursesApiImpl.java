package com.example.educore.courses.infrastructure;

import com.example.educore.courses.CoursesApi;
import com.example.educore.courses.domain.exceptions.CourseException;
import com.example.educore.courses.domain.model.AcademicYear;
import com.example.educore.courses.domain.model.AcademicYearRepository;
import com.example.educore.courses.domain.model.AcademicYearStatus;
import com.example.educore.sharedkernel.domain.Level;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class CoursesApiImpl implements CoursesApi {

    private final AcademicYearRepository academicYearRepository;

    @Override
    public int activeYear() {
        return activeYearOrThrow().getYear();
    }

    @Override
    public List<String> subjectsFor(Level level) {
        return activeYearOrThrow().planOf(level).getSubjects();
    }

    @Override
    public List<String> sectionsFor(Level level) {
        return activeYearOrThrow().planOf(level).getSections();
    }

    private AcademicYear activeYearOrThrow() {
        return academicYearRepository.findFirstByStatusOrderByYearDesc(AcademicYearStatus.ACTIVE)
                .orElseThrow(CourseException::noActiveYear);
    }
}
