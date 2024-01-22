package com.ipsen.spine.controller;

import com.ipsen.spine.controller.vo.RoleResult;
import com.ipsen.spine.model.Permission;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/permission")
public class PermissionController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Permission[] readAll(){
        return Permission.values();
    }

}
