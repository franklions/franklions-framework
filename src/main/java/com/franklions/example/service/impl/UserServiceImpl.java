package com.franklions.example.service.impl;

import com.franklions.example.domain.UserConverter;
import com.franklions.example.domain.UserDO;
import com.franklions.example.domain.UserDTO;
import com.franklions.example.mapper.UserMapper;
import com.franklions.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private UserConverter userConverter;

    @Autowired
    public UserServiceImpl(UserMapper userMapper,UserConverter userConverter) {
        this.userMapper = userMapper;
        this.userConverter = userConverter;
    }

    @Override
    public List<UserDTO> getAllUsers(){
        List<UserDO> listUser = userMapper.selectAll();
        List<UserDTO> listUserDTO =listUser.stream()
//                .map( u -> UserConverter.INSTANCE.domain2dto(u))        //通过实体转换器转换
                .map(u -> userConverter.domain2dto(u))
                .collect(Collectors.toList());
        return listUserDTO;
    }

    @Override
    public List<UserDTO> getUserByName(String name) {
        List<UserDO> listUser = userMapper.selectByName(name);
        List<UserDTO> listUserDTO = listUser.stream()
//                .map( u -> UserConverter.INSTANCE.domain2dto(u))        //通过实体转换器转换
                .map(u -> userConverter.domain2dto(u))
                .collect(Collectors.toList());
        return listUserDTO;
    }

    @Override
    public Optional<UserDTO> getUserByAccount(String account) {
        UserDO userDO = userMapper.selectByAccount(account);
//        UserDTO userDTO = UserConverter.INSTANCE.domain2dto(userDO);
        UserDTO userDTO = userConverter.domain2dto(userDO);
        return Optional.ofNullable(userDTO);
    }

    @Override
    public Optional<UserDTO> getUserById(Integer id) {
        UserDO userDO = userMapper.selectUserDetialById(id);
        if(userDO == null){
            return Optional.empty();
        }

        UserDTO userDTO = userConverter.domain2dto(userDO);

       return Optional.ofNullable(userDTO);
    }

    @Override
    public Optional<Integer> addUser(UserDTO user) {
//
//        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
//        Set<ConstraintViolation<T>> constraintViolations = validator.validate(想要验证的bean);
//        ConstraintViolation<T> constraintViolation = getFirst(constraintViolations, null);
//        if (constraintViolation != null) {
//            throw new ValidationException(constraintViolation.getMessage());
//        }

        userMapper.insert(userConverter.dto2do(user));
        return Optional.empty();
    }
}
