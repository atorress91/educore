package com.example.educore.auth.application.commands;

import com.example.educore.auth.application.dto.AuthResponse;
import com.example.educore.auth.domain.exceptions.AuthException;
import com.example.educore.auth.domain.model.Role;
import com.example.educore.auth.domain.model.User;
import com.example.educore.auth.domain.model.UserRepository;
import com.example.educore.auth.domain.security.TokenIssuer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterUserCommandHandlerTest {

    @Mock UserRepository userRepository;
    @Mock PasswordEncoder passwordEncoder;
    @Mock TokenIssuer tokenIssuer;

    @InjectMocks RegisterUserCommandHandler handler;

    @Test
    void creates_student_and_returns_token() {
        when(userRepository.existsByEmail("ada@example.com")).thenReturn(false);
        when(passwordEncoder.encode("secret123")).thenReturn("hashed");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));
        when(tokenIssuer.issueFor(any(User.class))).thenReturn("jwt-token");

        AuthResponse response = handler.handle(new RegisterUserCommand(
                "Ada", "Lovelace", "1234567", "ada@example.com", "secret123"));

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User saved = userCaptor.getValue();

        assertThat(response.token()).isEqualTo("jwt-token");
        assertThat(response.user().email()).isEqualTo("ada@example.com");
        assertThat(saved.getRole()).isEqualTo(Role.STUDENT);
        assertThat(saved.getPasswordHash()).isEqualTo("hashed");
        assertThat(saved.domainEvents()).hasSize(1);
    }

    @Test
    void rejects_duplicate_email() {
        when(userRepository.existsByEmail("ada@example.com")).thenReturn(true);

        assertThatThrownBy(() -> handler.handle(new RegisterUserCommand(
                "Ada", "Lovelace", "1234567", "ada@example.com", "secret123")))
                .isInstanceOf(AuthException.class)
                .hasMessageContaining("ya esta registrado");

        verify(userRepository, never()).save(any());
        verify(tokenIssuer, never()).issueFor(any());
    }
}
