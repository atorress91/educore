package com.example.educore.grades.application.commands;

import com.example.educore.sharedkernel.application.Command;
import com.example.educore.sharedkernel.domain.Level;

import java.util.List;
import java.util.UUID;

public record SaveGradesCommand(
        Level level,
        String section,
        String subject,
        List<Entry> entries
) implements Command<Boolean> {

    public record Entry(
            UUID studentId,
            Integer trim1,
            Integer trim2,
            Integer trim3,
            Integer ampl1,
            Integer ampl2
    ) {}
}
