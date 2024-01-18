package com.ipsen.spine.security;

import com.ipsen.spine.model.Permission;
import com.ipsen.spine.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PermissionChecks {

    public boolean hasPermission(String permissionText) {
        Permission permission = Permission.valueOf(permissionText);
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User)authentication.getPrincipal();
        if (currentUser == null) {
            return false;
        }
        return currentUser.getRole().hasPermission(permission);
    }
}
