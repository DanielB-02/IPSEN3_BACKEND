package com.ipsen.spine.model;

import lombok.Getter;

import java.util.Arrays;

import static com.ipsen.spine.model.Permission.*;

@Getter
public enum Role {
    READONLY(
            LEZEN),
    FICTER(
            LEZEN,
            DOMEIN_BEHEER_FICTER),
    ADMIN(
            LEZEN,
            DOMEIN_BEHEER_ADMIN,
            DOMEIN_BEHEER_FICTER,
            BEHEER_GEBRUIKERS,
            BEHEER_ROLLEN);

    private Permission[] permissions;

    Role(Permission... permissions) {
        this.permissions = permissions;
    }

    public boolean hasPermission(Permission matchPermission) {
        return Arrays.stream(permissions)
                .anyMatch(permission -> matchPermission == permission);
    }
}
