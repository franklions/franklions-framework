package com.franklions.example.controller;

import com.franklions.example.domain.RoleDTO;
import com.franklions.example.domain.RoleMenuDTO;
import com.franklions.example.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-12-12
 * @since Jdk 1.8
 */
@Validated
@RestController
public class RoleController {

    @Autowired
    IRoleService roleService;

    @GetMapping("/api/role/{id}")
    public RoleMenuDTO getRoleInfo(@NotNull @Valid @PathVariable("id") Integer id){
        Optional<RoleMenuDTO> roleOpt = roleService.getRoleMenusById(id);

        return roleOpt.get();
    }

    @PostMapping("/api/role")
    public String addRole(@NotNull @Valid @RequestBody RoleDTO request){
        roleService.addRole(request);
        return "SUCCESS";
    }

    @DeleteMapping("/api/role/{id}")
    public String removeRole(@PathVariable("id") Integer id){
        roleService.removeRole(id);
        return "SUCCESS";
    }
}
