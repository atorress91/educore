package com.example.educore.auth.application.commands;

import com.example.educore.auth.application.authorization.RolePermissionService;
import com.example.educore.auth.application.dto.UserResponse;
import com.example.educore.auth.domain.exceptions.AuthException;
import com.example.educore.auth.domain.model.User;
import com.example.educore.auth.domain.model.UserRepository;
import com.example.educore.sharedkernel.application.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PromoteUserCommandHandler implements CommandHandler<PromoteUserCommand, UserResponse> {

    private final UserRepository userRepository;
    private final RolePermissionService rolePermissionService;

    @Override
    public UserResponse handle(PromoteUserCommand command) {
        User user = userRepository.findById(command.userId())
                .orElseThrow(AuthException::userNotFound);

        user.promoteTo(command.newRole());
        userRepository.save(user);

        return UserResponse.of(user, rolePermissionService.effectiveFor(user.getRole()));
    }
}
