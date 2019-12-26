package com.franklions.example.service;

import com.franklions.example.domain.UserDTO;

import java.util.List;
import java.util.Optional;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2019/1/31
 * @since Jdk 1.8
 */
public interface UserService {
    List<UserDTO> getAllUsers();

    List<UserDTO> getUserByName(String name);

    Optional<UserDTO> getUserByAccount(String account);

    Optional<UserDTO> getUserById(Integer id);

    Optional<Integer> addUser(UserDTO user);

    void removeUser(Integer id);

    void editUser(UserDTO request);
}
