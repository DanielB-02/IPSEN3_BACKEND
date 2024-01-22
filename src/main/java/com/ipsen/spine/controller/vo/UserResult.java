package com.ipsen.spine.controller.vo;

import com.ipsen.spine.model.Role;
import com.ipsen.spine.model.User;

public class UserResult {
    public Long id;
    public String firstName;
    public String lastName;
    public String email;
    public String role;

    public static UserResult create(User user) {
        UserResult result = new UserResult();
        result.id = user.getId();
        result.email = user.getEmail();
        result.firstName = user.getFirstName();
        result.lastName = user.getLastName();
        result.role = user.getRole().getName();
        return result;
    }
}
