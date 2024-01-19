package com.ipsen.spine.service;

import com.ipsen.spine.controller.vo.RolePermissionForm;
import com.ipsen.spine.exception.NotFoundException;
import com.ipsen.spine.model.Permission;
import com.ipsen.spine.model.Role;
import com.ipsen.spine.repository.RoleRepository;
import com.ipsen.spine.security.PermissionBeheerRollen;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Role update(long id, RolePermissionForm form) {
        Optional<Role> fetchedRole = roleRepository.findById(id);
        if(fetchedRole.isEmpty()){
            throw new NotFoundException("Platform with id: " + id + " not found");
        }
        Role role = fetchedRole.get();
        role.setName(form.name);
        List<Permission> permissions = new ArrayList<>();
        for (String permissionText : form.permissions) {
            permissions.add(Permission.valueOf(permissionText));
        }
        role.setPermissions(permissions);
        return roleRepository.save(role);
    }
}
