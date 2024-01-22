package com.ipsen.spine.controller.vo;

import com.ipsen.spine.model.Role;

import java.util.ArrayList;
import java.util.List;

public class RoleResult {
    public Long id;
    public String name;

    public static List<RoleResult> toResult(Iterable<Role> roles) {
        List<RoleResult> results = new ArrayList<>();
        for (Role role : roles) {
            RoleResult result = new RoleResult();
            result.id = role.getId();
            result.name = role.getName();
            results.add(result);
        }
        return results;
    }
}
