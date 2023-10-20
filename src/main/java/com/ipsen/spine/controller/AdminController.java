package com.ipsen.spine.controller;

import com.ipsen.spine.dao.AdminDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private AdminDAO adminService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String admin(){
        return adminService.youAreAnAdmin();
    }

}