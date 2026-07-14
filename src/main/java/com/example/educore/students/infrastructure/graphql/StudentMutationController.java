package com.example.educore.students.infrastructure.graphql;

import com.example.educore.sharedkernel.application.Mediator;
import com.example.educore.students.application.commands.DeleteStudentCommand;
import com.example.educore.students.application.commands.EnrollStudentCommand;
import com.example.educore.students.application.dto.StudentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Controller
@Validated
@PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
@RequiredArgsConstructor
public class StudentMutationController {

    private final Mediator mediator;

    @MutationMapping
    public StudentResponse enrollStudent(@Argument @Valid EnrollStudentInput input) {
        return mediator.send(new EnrollStudentCommand(
                input.firstName(),
                input.lastName(),
                input.identification(),
                input.phone(),
                input.level(),
                input.section(),
                input.province(),
                input.canton(),
                input.district(),
                input.address(),
                input.guardianName(),
                input.guardianPhone(),
                input.subjects()
        ));
    }

    @MutationMapping
    public Boolean deleteStudent(@Argument UUID id) {
        return mediator.send(new DeleteStudentCommand(id));
    }
}
