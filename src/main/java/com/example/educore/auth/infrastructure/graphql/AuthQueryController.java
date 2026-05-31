package com.example.educore.auth.infrastructure.graphql;

import com.example.educore.auth.application.dto.UserResponse;
import com.example.educore.auth.application.queries.GetCurrentUserQuery;
import com.example.educore.auth.domain.exceptions.AuthException;
import com.example.educore.sharedkernel.application.Mediator;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AuthQueryController {

    private final Mediator mediator;

    @QueryMapping
    public UserResponse me(@AuthenticationPrincipal UUID userId) {
        if (userId == null) {
            throw AuthException.notAuthenticated();
        }
        return mediator.ask(new GetCurrentUserQuery(userId));
    }
}
