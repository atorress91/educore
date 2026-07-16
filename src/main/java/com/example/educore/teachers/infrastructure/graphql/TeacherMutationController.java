package com.example.educore.teachers.infrastructure.graphql;

import com.example.educore.sharedkernel.application.Mediator;
import com.example.educore.sharedkernel.domain.Level;
import com.example.educore.teachers.application.commands.AssignTeacherCommand;
import com.example.educore.teachers.application.commands.CreateTeacherCommand;
import com.example.educore.teachers.application.commands.DeleteTeacherCommand;
import com.example.educore.teachers.application.commands.UnassignTeacherCommand;
import com.example.educore.teachers.application.commands.UpdateTeacherCommand;
import com.example.educore.teachers.application.dto.TeacherAssignmentResponse;
import com.example.educore.teachers.application.dto.TeacherResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.UUID;

@Controller
@Validated
@PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
@RequiredArgsConstructor
public class TeacherMutationController {

    private final Mediator mediator;

    @MutationMapping
    public TeacherResponse createTeacher(@Argument @Valid CreateTeacherInput input) {
        return mediator.send(new CreateTeacherCommand(
                input.fullName(),
                input.email(),
                input.phone(),
                input.subject(),
                LocalDate.parse(input.hiredAt()),
                input.status()
        ));
    }

    @MutationMapping
    public TeacherResponse updateTeacher(@Argument UUID id, @Argument @Valid UpdateTeacherInput input) {
        return mediator.send(new UpdateTeacherCommand(
                id,
                input.fullName(),
                input.email(),
                input.phone(),
                input.subject(),
                LocalDate.parse(input.hiredAt()),
                input.status()
        ));
    }

    @MutationMapping
    public Boolean deleteTeacher(@Argument UUID id) {
        return mediator.send(new DeleteTeacherCommand(id));
    }

    @MutationMapping
    public TeacherAssignmentResponse assignTeacher(@Argument Level level, @Argument String section,
                                                   @Argument String subject, @Argument UUID teacherId) {
        return mediator.send(new AssignTeacherCommand(level, section, subject, teacherId));
    }

    @MutationMapping
    public Boolean unassignTeacher(@Argument Level level, @Argument String section, @Argument String subject) {
        return mediator.send(new UnassignTeacherCommand(level, section, subject));
    }
}
