package com.example.educore.attendance.application.queries;

import com.example.educore.attendance.application.dto.AttendanceSummaryResponse;
import com.example.educore.attendance.domain.model.AttendanceRecord;
import com.example.educore.attendance.domain.model.AttendanceRepository;
import com.example.educore.attendance.domain.model.AttendanceStatus;
import com.example.educore.sharedkernel.application.QueryHandler;
import com.example.educore.sharedkernel.domain.Level;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Aggregates attendance counts per class slot (level+section+subject) for a
 * date — powers the admin overview.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetAttendanceSummaryQueryHandler
        implements QueryHandler<GetAttendanceSummaryQuery, List<AttendanceSummaryResponse>> {

    private final AttendanceRepository attendanceRepository;

    private record Slot(Level level, String section, String subject) {}

    @Override
    public List<AttendanceSummaryResponse> handle(GetAttendanceSummaryQuery query) {
        Map<Slot, int[]> counts = new LinkedHashMap<>();
        for (AttendanceRecord record : attendanceRepository.findByDate(query.date())) {
            Slot slot = new Slot(record.getLevel(), record.getSection(), record.getSubject());
            int[] c = counts.computeIfAbsent(slot, k -> new int[4]);
            c[indexOf(record.getStatus())]++;
        }

        List<AttendanceSummaryResponse> result = new ArrayList<>();
        counts.forEach((slot, c) -> result.add(new AttendanceSummaryResponse(
                slot.level(), slot.section(), slot.subject(), c[0], c[1], c[2], c[3])));
        return result;
    }

    private int indexOf(AttendanceStatus status) {
        return switch (status) {
            case PRESENT -> 0;
            case ABSENT -> 1;
            case LATE -> 2;
            case JUSTIFIED -> 3;
        };
    }
}
