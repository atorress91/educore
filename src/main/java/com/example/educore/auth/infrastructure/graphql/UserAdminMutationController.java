package com.example.educore.auth.infrastructure.graphql;

import com.example.educore.auth.application.commands.PromoteUserCommand;
import com.example.educore.auth.application.commands.PromoteUserCommandHandler;
import com.example.educore.auth.application.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

@Controller
@Validated
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class UserAdminMutationController {

    private final PromoteUserCommandHandler promoteHandler;

    @MutationMapping
    public UserResponse promoteUser(@Argument @Valid PromoteUserInput input) {
        return promoteHandler.handle(new PromoteUserCommand(input.userId(), input.newRole()));
    }
}
