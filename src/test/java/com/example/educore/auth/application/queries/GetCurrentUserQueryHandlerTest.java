package com.example.educore.auth.application.queries;

import com.example.educore.auth.application.dto.UserResponse;
import com.example.educore.auth.domain.exceptions.AuthException;
import com.example.educore.auth.domain.model.Role;
import com.example.educore.auth.domain.model.User;
import com.example.educore.auth.domain.model.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetCurrentUserQueryHandlerTest {

    @Mock UserRepository userRepository;
    @InjectMocks GetCurrentUserQueryHandler handler;

    @Test
    void returns_current_user_when_exists() {
        User user = User.register("Ada", "Lovelace", "1234567", "ada@example.com", "h", Role.STUDENT);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        UserResponse response = handler.handle(new GetCurrentUserQuery(user.getId()));

        assertThat(response.id()).isEqualTo(user.getId());
        assertThat(response.email()).isEqualTo("ada@example.com");
        assertThat(response.role()).isEqualTo(Role.STUDENT);
    }

    @Test
    void throws_when_user_missing() {
        UUID missing = UUID.randomUUID();
        when(userRepository.findById(missing)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> handler.handle(new GetCurrentUserQuery(missing)))
                .isInstanceOf(AuthException.class)
                .hasMessageContaining("no encontrado");
    }
}
