package com.ipsen.spine.controller.vo;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class RolePermissionForm {
    @NotNull
    public String name;
    public List<String> permissions;
}
