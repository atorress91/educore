package com.example.educore.auth.infrastructure.graphql;

import com.example.educore.auth.application.dto.RolePermissionsView;
import com.example.educore.auth.application.queries.GetRolePermissionsQuery;
import com.example.educore.sharedkernel.application.Mediator;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class RolePermissionQueryController {

    private final Mediator mediator;

    @QueryMapping
    public List<RolePermissionsView> rolePermissions() {
        return mediator.ask(new GetRolePermissionsQuery());
    }
}
