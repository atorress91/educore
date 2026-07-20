package com.example.educore.auth.application.commands;

import com.example.educore.auth.application.authorization.RolePermissionService;
import com.example.educore.auth.application.dto.RolePermissionsView;
import com.example.educore.sharedkernel.application.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateRolePermissionsCommandHandler
        implements CommandHandler<UpdateRolePermissionsCommand, List<RolePermissionsView>> {

    private final RolePermissionService rolePermissionService;

    @Override
    public List<RolePermissionsView> handle(UpdateRolePermissionsCommand command) {
        return rolePermissionService.update(command.entries());
    }
}
