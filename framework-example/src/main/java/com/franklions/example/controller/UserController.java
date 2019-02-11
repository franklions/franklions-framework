package com.franklions.example.controller;

import com.franklions.example.domain.UserDTO;
import com.franklions.example.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @ApiOperation(value = "根据名称获取用户信息",notes = "根据名称获取用户信息")
    @ApiImplicitParam(name = "name",value = "用户名称")
    @GetMapping(value = "/api/user",params = "name")
    public List<UserDTO> getUserByName(@RequestParam(name = "name") String name){
        return userService.getUserByName(name);
    }

    @ApiOperation(value = "根据帐号获取用户信息",notes = "根据帐号获取用户信息")
    @ApiImplicitParam(name = "account",value = "用户帐号")
    @GetMapping(value = "/api/user/acc",params = "account")
    public UserDTO getUserByAccount(@RequestParam(name = "account") String account){
        return userService.getUserByAccount(account);
    }
}
