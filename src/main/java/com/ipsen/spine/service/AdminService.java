package com.ipsen.spine.service;

import com.ipsen.spine.security.AdminSecurity;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @AdminSecurity
    public String youAreAnAdmin() {
        return "Yes, you're an admin!";
    }
}
