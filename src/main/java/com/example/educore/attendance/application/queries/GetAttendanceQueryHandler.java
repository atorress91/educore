package com.example.educore.attendance.application.queries;

import com.example.educore.attendance.application.dto.AttendanceEntryResponse;
import com.example.educore.attendance.domain.model.AttendanceRepository;
import com.example.educore.sharedkernel.application.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetAttendanceQueryHandler
        implements QueryHandler<GetAttendanceQuery, List<AttendanceEntryResponse>> {

    private final AttendanceRepository attendanceRepository;

    @Override
    public List<AttendanceEntryResponse> handle(GetAttendanceQuery query) {
        return attendanceRepository
                .findByDateAndLevelAndSectionAndSubject(query.date(), query.level(), query.section(), query.subject())
                .stream()
                .map(r -> new AttendanceEntryResponse(r.getStudentId(), r.getStatus()))
                .toList();
    }
}
