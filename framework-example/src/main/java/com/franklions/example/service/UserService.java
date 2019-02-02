package com.franklions.example.service;

import com.franklions.example.domain.UserDO;
import com.franklions.example.domain.UserDTO;

import java.util.List;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2019/1/31
 * @since Jdk 1.8
 */
public interface UserService {
    List<UserDTO> getAllUsers();
}
