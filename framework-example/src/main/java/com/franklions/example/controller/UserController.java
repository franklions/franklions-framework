package com.franklions.example.controller;

import com.franklions.example.domain.UserDTO;
import com.franklions.example.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2019/2/2
 * @since Jdk 1.8
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取所有用户信息",notes = "获取所有用户信息")
    @GetMapping(value = "/api/alluser")
    public List<UserDTO> getAllUser(){
        return userService.getAllUsers();
    }
}
