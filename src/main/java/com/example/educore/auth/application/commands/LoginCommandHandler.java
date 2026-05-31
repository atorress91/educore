package com.example.educore.auth.application.commands;

import com.example.educore.auth.application.dto.AuthResponse;
import com.example.educore.auth.application.dto.UserResponse;
import com.example.educore.auth.domain.exceptions.AuthException;
import com.example.educore.auth.domain.model.User;
import com.example.educore.auth.domain.model.UserRepository;
import com.example.educore.auth.domain.security.TokenIssuer;
import com.example.educore.sharedkernel.application.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginCommandHandler implements CommandHandler<LoginCommand, AuthResponse> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenIssuer tokenIssuer;

    @Override
    public AuthResponse handle(LoginCommand command) {
        User user = userRepository.findByEmail(command.email())
                .orElseThrow(AuthException::invalidCredentials);

        if (!passwordEncoder.matches(command.password(), user.getPasswordHash())) {
            throw AuthException.invalidCredentials();
        }

        String token = tokenIssuer.issueFor(user);
        return new AuthResponse(token, UserResponse.fromUser(user));
    }
}
