package com.ipsen.spine.service;

import com.ipsen.spine.controller.vo.RolePermissionForm;
import com.ipsen.spine.model.Permission;
import com.ipsen.spine.model.Role;
import com.ipsen.spine.repository.RoleRepository;
import com.ipsen.spine.security.PermissionBeheerRollen;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @PermissionBeheerRollen
    public Role create(RolePermissionForm form) {
        Role entityToSave = new Role();
        entityToSave.setName(form.name);
        entityToSave.setPermissions(form.permissions.stream()
                .map(Permission::valueOf)
                .toList());
        return roleRepository.save(entityToSave);
    }
}
