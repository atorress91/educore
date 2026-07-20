package com.example.educore.auth.application.queries;

import com.example.educore.auth.application.authorization.RolePermissionService;
import com.example.educore.auth.application.dto.RolePermissionsView;
import com.example.educore.sharedkernel.application.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetRolePermissionsQueryHandler
        implements QueryHandler<GetRolePermissionsQuery, List<RolePermissionsView>> {

    private final RolePermissionService rolePermissionService;

    @Override
    public List<RolePermissionsView> handle(GetRolePermissionsQuery query) {
        return rolePermissionService.matrix();
    }
}
