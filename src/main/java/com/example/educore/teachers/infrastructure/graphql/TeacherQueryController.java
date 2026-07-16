package com.example.educore.teachers.infrastructure.graphql;

import com.example.educore.sharedkernel.application.Mediator;
import com.example.educore.sharedkernel.domain.Level;
import com.example.educore.teachers.application.dto.TeacherAssignmentResponse;
import com.example.educore.teachers.application.dto.TeacherGroupResponse;
import com.example.educore.teachers.application.dto.TeacherResponse;
import com.example.educore.teachers.application.queries.GetAllAssignmentsQuery;
import com.example.educore.teachers.application.queries.GetAssignmentsQuery;
import com.example.educore.teachers.application.queries.GetMyGroupsQuery;
import com.example.educore.teachers.application.queries.GetTeachersQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class TeacherQueryController {

    private final Mediator mediator;

    @QueryMapping
    public List<TeacherResponse> teachers() {
        return mediator.ask(new GetTeachersQuery());
    }

    @QueryMapping
    public List<TeacherAssignmentResponse> assignments(@Argument Level level) {
        return mediator.ask(new GetAssignmentsQuery(level));
    }

    @QueryMapping
    public List<TeacherAssignmentResponse> allAssignments() {
        return mediator.ask(new GetAllAssignmentsQuery());
    }

    @QueryMapping
    public List<TeacherGroupResponse> myGroups(@AuthenticationPrincipal UUID userId) {
        return mediator.ask(new GetMyGroupsQuery(userId));
    }
}
