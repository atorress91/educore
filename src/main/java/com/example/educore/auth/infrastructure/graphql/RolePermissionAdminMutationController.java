package com.example.educore.auth.infrastructure.graphql;

import com.example.educore.auth.application.commands.UpdateRolePermissionsCommand;
import com.example.educore.auth.application.dto.RolePermissionsView;
import com.example.educore.auth.domain.authorization.RolePermissionEntry;
import com.example.educore.sharedkernel.application.Mediator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Controller
@Validated
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class RolePermissionAdminMutationController {

    private final Mediator mediator;

    @MutationMapping
    public List<RolePermissionsView> updateRolePermissions(@Argument @Valid UpdateRolePermissionsInput input) {
        List<RolePermissionEntry> entries = input.entries().stream()
                .map(e -> new RolePermissionEntry(e.role(), e.module(), e.level()))
                .toList();
        return mediator.send(new UpdateRolePermissionsCommand(entries));
    }
}
