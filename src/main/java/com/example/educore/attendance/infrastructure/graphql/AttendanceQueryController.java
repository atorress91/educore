package com.example.educore.attendance.infrastructure.graphql;

import com.example.educore.attendance.application.dto.AttendanceEntryResponse;
import com.example.educore.attendance.application.dto.AttendanceSummaryResponse;
import com.example.educore.attendance.application.queries.GetAttendanceQuery;
import com.example.educore.attendance.application.queries.GetAttendanceSummaryQuery;
import com.example.educore.sharedkernel.application.Mediator;
import com.example.educore.sharedkernel.domain.Level;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

@Controller
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class AttendanceQueryController {

    private final Mediator mediator;

    @QueryMapping
    public List<AttendanceEntryResponse> attendance(@Argument Level level, @Argument String section,
                                                    @Argument String subject, @Argument String date) {
        return mediator.ask(new GetAttendanceQuery(level, section, subject, LocalDate.parse(date)));
    }

    @QueryMapping
    public List<AttendanceSummaryResponse> attendanceSummary(@Argument String date) {
        return mediator.ask(new GetAttendanceSummaryQuery(LocalDate.parse(date)));
    }
}
