package com.franklions.example.dao;

import com.franklions.example.domain.UserDO;

import java.util.List;
import java.util.Optional;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-12-09
 * @since Jdk 1.8
 */
public interface IUserDAO {
    List<UserDO> selectAll();

    List<UserDO> selectByName(String name);

    UserDO selectByAccount(String account);

    Optional<UserDO> selectUserDetialById(Integer id);

    UserDO insert(UserDO userDO);
}
