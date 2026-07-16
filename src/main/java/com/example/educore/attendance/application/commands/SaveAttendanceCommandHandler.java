package com.example.educore.attendance.application.commands;

import com.example.educore.attendance.domain.model.AttendanceRecord;
import com.example.educore.attendance.domain.model.AttendanceRepository;
import com.example.educore.sharedkernel.application.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SaveAttendanceCommandHandler implements CommandHandler<SaveAttendanceCommand, Boolean> {

    private final AttendanceRepository attendanceRepository;

    @Override
    public Boolean handle(SaveAttendanceCommand command) {
        for (SaveAttendanceCommand.Entry entry : command.entries()) {
            AttendanceRecord record = attendanceRepository
                    .findByDateAndLevelAndSectionAndSubjectAndStudentId(
                            command.date(), command.level(), command.section(), command.subject(), entry.studentId())
                    .map(existing -> {
                        existing.changeStatus(entry.status());
                        return existing;
                    })
                    .orElseGet(() -> new AttendanceRecord(
                            command.date(), command.level(), command.section(), command.subject(),
                            entry.studentId(), entry.status()));
            attendanceRepository.save(record);
        }
        return true;
    }
}
