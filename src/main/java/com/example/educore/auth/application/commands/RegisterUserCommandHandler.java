package com.example.educore.auth.application.commands;

import com.example.educore.auth.application.dto.AuthResponse;
import com.example.educore.auth.application.dto.UserResponse;
import com.example.educore.auth.domain.exceptions.AuthException;
import com.example.educore.auth.domain.model.Role;
import com.example.educore.auth.domain.model.User;
import com.example.educore.auth.domain.model.UserRepository;
import com.example.educore.auth.domain.security.TokenIssuer;
import com.example.educore.sharedkernel.application.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterUserCommandHandler implements CommandHandler<RegisterUserCommand, AuthResponse> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenIssuer tokenIssuer;

    @Override
    public AuthResponse handle(RegisterUserCommand command) {
        if (userRepository.existsByEmail(command.email())) {
            throw AuthException.emailAlreadyExists(command.email());
        }

        String hashedPassword = passwordEncoder.encode(command.password());

        User user = User.register(
                command.name(),
                command.lastName(),
                command.identification(),
                command.email(),
                hashedPassword,
                Role.STUDENT
        );

        userRepository.save(user);

        String token = tokenIssuer.issueFor(user);
        return new AuthResponse(token, UserResponse.fromUser(user));
    }
}
