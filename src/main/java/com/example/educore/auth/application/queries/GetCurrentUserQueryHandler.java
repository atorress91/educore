package com.example.educore.auth.application.queries;

import com.example.educore.auth.application.authorization.RolePermissionService;
import com.example.educore.auth.application.dto.UserResponse;
import com.example.educore.auth.domain.exceptions.AuthException;
import com.example.educore.auth.domain.model.UserRepository;
import com.example.educore.sharedkernel.application.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetCurrentUserQueryHandler implements QueryHandler<GetCurrentUserQuery, UserResponse> {

    private final UserRepository userRepository;
    private final RolePermissionService rolePermissionService;

    @Override
    public UserResponse handle(GetCurrentUserQuery query) {
        return userRepository.findById(query.userId())
                .map(user -> UserResponse.of(user, rolePermissionService.effectiveFor(user.getRole())))
                .orElseThrow(AuthException::userNotFound);
    }
}
