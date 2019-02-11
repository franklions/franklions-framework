package com.franklions.example.service.impl;

import com.franklions.example.domain.UserDO;
import com.franklions.example.domain.UserDTO;
import com.franklions.example.mapper.UserMapper;
import com.franklions.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2019/1/31
 * @since Jdk 1.8
 */
@Service
public class UserServiceImpl extends AbstractBaseService implements UserService {

    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> getAllUsers(){
        List<UserDO> listUser = userMapper.selectAll();
        List<UserDTO> listUserDTO =new ArrayList<>();
        listUser.forEach(u->{listUserDTO.add(new UserDTO(u));});
        return listUserDTO;
    }

    @Override
    public List<UserDTO> getUserByName(String name) {
        List<UserDO> listUser = userMapper.selectByName(name);
        List<UserDTO> listUserDTO = new ArrayList<>();
        listUser.forEach(u->{listUserDTO.add(new UserDTO(u));});
        return listUserDTO;
    }

    @Override
    public UserDTO getUserByAccount(String account) {
        UserDO userDO = userMapper.selectByAccount(account);
        UserDTO userDTO = new UserDTO(userDO);
        return userDTO;
    }
}
