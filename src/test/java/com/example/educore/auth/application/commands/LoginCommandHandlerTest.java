package com.example.educore.auth.application.commands;

import com.example.educore.auth.application.dto.AuthResponse;
import com.example.educore.auth.domain.exceptions.AuthException;
import com.example.educore.auth.domain.model.Role;
import com.example.educore.auth.domain.model.User;
import com.example.educore.auth.domain.model.UserRepository;
import com.example.educore.auth.domain.security.TokenIssuer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginCommandHandlerTest {

    @Mock UserRepository userRepository;
    @Mock PasswordEncoder passwordEncoder;
    @Mock TokenIssuer tokenIssuer;

    @InjectMocks LoginCommandHandler handler;

    private User existingUser;

    @BeforeEach
    void setUp() {
        existingUser = User.register("Ada", "Lovelace", "1234567", "ada@example.com", "hashed", Role.STUDENT);
    }

    @Test
    void returns_token_on_valid_credentials() {
        when(userRepository.findByEmail("ada@example.com")).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches("secret123", "hashed")).thenReturn(true);
        when(tokenIssuer.issueFor(existingUser)).thenReturn("jwt-token");

        AuthResponse response = handler.handle(new LoginCommand("ada@example.com", "secret123"));

        assertThat(response.token()).isEqualTo("jwt-token");
        assertThat(response.user().id()).isEqualTo(existingUser.getId());
    }

    @Test
    void fails_when_password_does_not_match() {
        when(userRepository.findByEmail("ada@example.com")).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThatThrownBy(() -> handler.handle(new LoginCommand("ada@example.com", "wrong")))
                .isInstanceOf(AuthException.class)
                .hasMessageContaining("incorrectos");

        verify(tokenIssuer, never()).issueFor(any());
    }

    @Test
    void fails_when_email_unknown() {
        when(userRepository.findByEmail("ghost@example.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> handler.handle(new LoginCommand("ghost@example.com", "x")))
                .isInstanceOf(AuthException.class);
    }
}
