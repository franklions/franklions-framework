package com.franklions.example.dao.impl;

import com.franklions.example.dao.IUserDAO;
import com.franklions.example.domain.UserDO;
import com.franklions.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-12-09
 * @since Jdk 1.8
 */
@Repository("userDAO")
public class UserDAOImpl implements IUserDAO {
    @Autowired
    UserRepository userRepo;

    @Override
    public List<UserDO> selectAll() {
        return userRepo.findAll();
    }

    @Override
    public List<UserDO> selectByName(String name) {
        return userRepo.findByNameLike(name);
    }

    @Override
    public UserDO selectByAccount(String account) {
        return userRepo.findByAccount(account);
    }

    @Override
    public Optional<UserDO> selectUserDetialById(Integer id) {
        return userRepo.findById(id);
    }

    @Override
    public void insert(UserDO userDO) {
        userRepo.saveAndFlush(userDO);
    }
}
