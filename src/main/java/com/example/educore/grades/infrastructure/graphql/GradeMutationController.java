package com.example.educore.grades.infrastructure.graphql;

import com.example.educore.grades.application.commands.SaveGradesCommand;
import com.example.educore.sharedkernel.application.Mediator;
import com.example.educore.sharedkernel.domain.Level;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Controller
@Validated
@PreAuthorize("@perm.can('GRADES','WRITE')")
@RequiredArgsConstructor
public class GradeMutationController {

    private final Mediator mediator;

    @MutationMapping
    public Boolean saveGrades(@Argument Level level, @Argument String section, @Argument String subject,
                              @Argument @Valid List<GradeEntryInput> entries) {
        List<SaveGradesCommand.Entry> mapped = entries.stream()
                .map(e -> new SaveGradesCommand.Entry(
                        e.studentId(), e.trim1(), e.trim2(), e.trim3(), e.ampl1(), e.ampl2()))
                .toList();
        return mediator.send(new SaveGradesCommand(level, section, subject, mapped));
    }
}
