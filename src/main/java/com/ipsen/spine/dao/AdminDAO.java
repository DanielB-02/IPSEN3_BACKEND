package com.ipsen.spine.dao;

import com.ipsen.spine.security.AdminSecurity;
import org.springframework.stereotype.Service;

@Service
public class AdminDAO {

    @AdminSecurity
    public String youAreAnAdmin() {
        return "Yes, you're an admin!";
    }
}
