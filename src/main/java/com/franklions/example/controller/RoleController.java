package com.franklions.example.controller;

import com.franklions.example.domain.RoleDTO;
import com.franklions.example.domain.RoleMenuDTO;
import com.franklions.example.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-12-12
 * @since Jdk 1.8
 */
@RestController
public class RoleController {

    @Autowired
    IRoleService roleService;

    @GetMapping("/api/role/{id}")
    public RoleMenuDTO getRoleInfo(@PathVariable("id") Integer id){
        Optional<RoleMenuDTO> roleOpt = roleService.getRoleMenusById(id);

        return roleOpt.get();
    }
}
