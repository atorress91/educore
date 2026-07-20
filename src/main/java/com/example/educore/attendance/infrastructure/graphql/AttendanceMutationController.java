package com.example.educore.attendance.infrastructure.graphql;

import com.example.educore.attendance.application.commands.SaveAttendanceCommand;
import com.example.educore.sharedkernel.application.Mediator;
import com.example.educore.sharedkernel.domain.Level;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@Controller
@Validated
@PreAuthorize("@perm.can('ATTENDANCE','WRITE')")
@RequiredArgsConstructor
public class AttendanceMutationController {

    private final Mediator mediator;

    @MutationMapping
    public Boolean saveAttendance(@Argument Level level, @Argument String section, @Argument String subject,
                                  @Argument String date, @Argument @Valid List<AttendanceEntryInput> entries) {
        List<SaveAttendanceCommand.Entry> mapped = entries.stream()
                .map(e -> new SaveAttendanceCommand.Entry(e.studentId(), e.status()))
                .toList();
        return mediator.send(new SaveAttendanceCommand(level, section, subject, LocalDate.parse(date), mapped));
    }
}
