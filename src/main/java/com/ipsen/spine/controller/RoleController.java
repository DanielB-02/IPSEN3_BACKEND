package com.ipsen.spine.controller;

import com.ipsen.spine.controller.vo.RolePermissionForm;
import com.ipsen.spine.controller.vo.RoleResult;
import com.ipsen.spine.model.Permission;
import com.ipsen.spine.model.Platform;
import com.ipsen.spine.model.Role;
import com.ipsen.spine.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<RoleResult> readAll(){
        return RoleResult.toResult(this.roleService.readAll());
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Role create(@RequestBody @Valid RolePermissionForm form) {
        return this.roleService.create(form);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Role update(@PathVariable long id, @RequestBody @Valid RolePermissionForm form) {
        return this.roleService.update(id, form);
    }
}
