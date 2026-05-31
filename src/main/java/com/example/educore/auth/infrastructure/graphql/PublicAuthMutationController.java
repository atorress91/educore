package com.example.educore.auth.infrastructure.graphql;

import com.example.educore.auth.application.commands.LoginCommand;
import com.example.educore.auth.application.commands.LoginCommandHandler;
import com.example.educore.auth.application.commands.RegisterUserCommand;
import com.example.educore.auth.application.commands.RegisterUserCommandHandler;
import com.example.educore.auth.application.dto.AuthResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

@Controller
@Validated
@RequiredArgsConstructor
public class PublicAuthMutationController {

    private final RegisterUserCommandHandler registerHandler;
    private final LoginCommandHandler loginHandler;

    @MutationMapping
    public AuthResponse register(@Argument @Valid RegisterInput input) {
        return registerHandler.handle(new RegisterUserCommand(
                input.name(),
                input.lastName(),
                input.identification(),
                input.email(),
                input.password()
        ));
    }

    @MutationMapping
    public AuthResponse login(@Argument @Valid LoginInput input) {
        return loginHandler.handle(new LoginCommand(input.email(), input.password()));
    }
}
