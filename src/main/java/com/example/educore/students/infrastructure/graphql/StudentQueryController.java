package com.example.educore.students.infrastructure.graphql;

import com.example.educore.sharedkernel.application.Mediator;
import com.example.educore.students.application.dto.StudentResponse;
import com.example.educore.students.application.queries.GetStudentsQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class StudentQueryController {

    private final Mediator mediator;

    @QueryMapping
    public List<StudentResponse> students() {
        return mediator.ask(new GetStudentsQuery());
    }
}
