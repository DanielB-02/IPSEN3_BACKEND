package com.ipsen.spine.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "_role")
@Setter
@Getter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;

    @ElementCollection(targetClass = Permission.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "role_permissions")
    @Column(name = "permission")
    private Collection<Permission> permissions = new ArrayList<>();

    public boolean hasPermission(Permission matchPermission) {
        return permissions.stream()
                .anyMatch(permission -> matchPermission == permission);
    }

}

/*
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
*/