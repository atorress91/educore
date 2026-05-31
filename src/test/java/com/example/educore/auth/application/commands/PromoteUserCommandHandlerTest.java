package com.example.educore.auth.application.commands;

import com.example.educore.auth.application.dto.UserResponse;
import com.example.educore.auth.domain.exceptions.AuthException;
import com.example.educore.auth.domain.model.Role;
import com.example.educore.auth.domain.model.User;
import com.example.educore.auth.domain.model.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PromoteUserCommandHandlerTest {

    @Mock UserRepository userRepository;
    @InjectMocks PromoteUserCommandHandler handler;

    private User existingUser;

    @BeforeEach
    void setUp() {
        existingUser = User.register("Ada", "Lovelace", "1234567", "ada@example.com", "hashed", Role.STUDENT);
    }

    @Test
    void changes_role_and_saves() {
        when(userRepository.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        UserResponse response = handler.handle(new PromoteUserCommand(existingUser.getId(), Role.TEACHER));

        assertThat(response.role()).isEqualTo(Role.TEACHER);
        assertThat(existingUser.getRole()).isEqualTo(Role.TEACHER);
        verify(userRepository).save(existingUser);
    }

    @Test
    void fails_when_user_not_found() {
        UUID unknown = UUID.randomUUID();
        when(userRepository.findById(unknown)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> handler.handle(new PromoteUserCommand(unknown, Role.ADMIN)))
                .isInstanceOf(AuthException.class)
                .hasMessageContaining("no encontrado");
    }
}
