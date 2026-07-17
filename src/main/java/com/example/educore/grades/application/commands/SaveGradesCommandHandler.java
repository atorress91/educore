package com.example.educore.grades.application.commands;

import com.example.educore.courses.CoursesApi;
import com.example.educore.grades.domain.model.GradeRecord;
import com.example.educore.grades.domain.model.GradeRepository;
import com.example.educore.sharedkernel.application.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SaveGradesCommandHandler implements CommandHandler<SaveGradesCommand, Boolean> {

    private final GradeRepository gradeRepository;
    private final CoursesApi coursesApi;

    @Override
    public Boolean handle(SaveGradesCommand command) {
        int year = coursesApi.activeYear();
        for (SaveGradesCommand.Entry entry : command.entries()) {
            GradeRecord record = gradeRepository
                    .findByYearAndLevelAndSectionAndSubjectAndStudentId(
                            year, command.level(), command.section(), command.subject(), entry.studentId())
                    .orElseGet(() -> new GradeRecord(
                            year, command.level(), command.section(), command.subject(), entry.studentId()));
            record.updateScores(entry.trim1(), entry.trim2(), entry.trim3(), entry.ampl1(), entry.ampl2());
            gradeRepository.save(record);
        }
        return true;
    }
}
