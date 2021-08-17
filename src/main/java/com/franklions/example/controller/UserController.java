package com.franklions.example.controller;

import com.franklions.example.domain.dto.UserDTO;
import com.franklions.example.domain.request.UserRequest;
import com.franklions.example.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2019/2/2
 * @since Jdk 1.8
 */
@Validated
@Api(tags="用户管理",description = "用户增册改查")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "添加用户",notes = "添加用户")
    @PostMapping("/user")
    public String addUser(@RequestBody @NotNull @Valid UserRequest user){
        userService.addUser(user);
        return "SUCCESS";
    }

    @ApiOperation(value = "获取所有用户信息",notes = "获取所有用户信息")
    @GetMapping(value = "/alluser")
    public List<UserDTO> getAllUser(){
        return userService.getAllUsers();
    }

    @ApiOperation(value = "根据名称获取用户信息",notes = "根据名称获取用户信息")
    @ApiImplicitParam(name = "name",value = "用户名称")
    @GetMapping(value = "/user",params = "name")
    public List<UserDTO> getUserByName(@RequestParam(name = "name") @NotNull @Size(min = 2, max = 30, message = "2<长度<30") String name){
        return userService.getUserByName(name);
    }

    @ApiOperation(value = "根据帐号获取用户信息",notes = "根据帐号获取用户信息")
    @ApiImplicitParam(name = "account",value = "用户帐号")
    @GetMapping(value = "/user/acc",params = "account")
    public UserDTO getUserByAccount(@RequestParam(name = "account") String account){
        //返回Optional避免NPE问题
        Optional<UserDTO> dtoOpt = userService.getUserByAccount(account);
        if(dtoOpt.isPresent()){
            return dtoOpt.get();
        }
        return null;
    }


    @ApiOperation(value = "根据帐号获取用户信息",notes = "根据帐号获取用户信息")
    @ApiImplicitParam(name = "account",value = "用户帐号")
    @GetMapping(value = "/user/{id}")
    public UserDTO getUserById(@PathVariable("id") @NotNull @Min(value=1,message = "id 不能小于1") Integer id){
        Optional<UserDTO> dtoOpt = userService.getUserById(id);

        if(dtoOpt.isPresent()){
            return dtoOpt.get();
        }

        return null;
    }

}
