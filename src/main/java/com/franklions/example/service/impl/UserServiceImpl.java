package com.franklions.example.service.impl;

import com.franklions.example.dao.IUserDAO;
import com.franklions.example.domain.DeptDO;
import com.franklions.example.domain.mapper.UserConverter;
import com.franklions.example.domain.UserDO;
import com.franklions.example.domain.UserDTO;
import com.franklions.example.repository.DeptRepository;
import com.franklions.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

    private IUserDAO userDAO;
    private UserConverter userConverter;
    private DeptRepository deptRepo ;

    @Autowired
    public UserServiceImpl(IUserDAO userDAO,UserConverter userConverter,DeptRepository deptRepo) {
        this.userDAO = userDAO;
        this.userConverter = userConverter;
        this.deptRepo = deptRepo;
    }

    @Override
    public List<UserDTO> getAllUsers(){
        List<UserDO> listUser = userDAO.selectAll();
        List<UserDTO> listUserDTO =listUser.stream()
//                .map( u -> UserConverter.INSTANCE.domain2dto(u))        //通过实体转换器转换
                .map(u -> userConverter.domain2dto(u))
                .collect(Collectors.toList());
        return listUserDTO;
    }

    @Override
    public List<UserDTO> getUserByName(String name) {
        List<UserDO> listUser = userDAO.selectByName(name);
        List<UserDTO> listUserDTO = listUser.stream()
//                .map( u -> UserConverter.INSTANCE.domain2dto(u))        //通过实体转换器转换
                .map(u -> userConverter.domain2dto(u))
                .collect(Collectors.toList());
        return listUserDTO;
    }

    @Override
    public Optional<UserDTO> getUserByAccount(String account) {
        UserDO userDO = userDAO.selectByAccount(account);
//        UserDTO userDTO = UserConverter.INSTANCE.domain2dto(userDO);
        UserDTO userDTO = userConverter.domain2dto(userDO);
        return Optional.ofNullable(userDTO);
    }

    @Override
    public Optional<UserDTO> getUserById(Integer id) {
        Optional<UserDO> userDO = userDAO.selectUserDetialById(id);
        if(!userDO.isPresent()){
            return Optional.empty();
        }

        UserDTO userDTO = userConverter.domain2dto(userDO.get());

       return Optional.ofNullable(userDTO);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public Optional<Integer> addUser(UserDTO user) {
//
//        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
//        Set<ConstraintViolation<T>> constraintViolations = validator.validate(想要验证的bean);
//        ConstraintViolation<T> constraintViolation = getFirst(constraintViolations, null);
//        if (constraintViolation != null) {
//            throw new ValidationException(constraintViolation.getMessage());
//        }
        UserDO newUser = userConverter.dto2do(user);
        newUser.setCreatetime(new Date());
        newUser.setPassword(UUID.randomUUID().toString());
        newUser.setDeleted(false);
        UserDO userDO = userDAO.insert(newUser);
        return Optional.ofNullable(userDO.getId());
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void removeUser(Integer id) {
        userDAO.deleteById(id);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void editUser(UserDTO dto) {
        UserDO userDO = userConverter.dto2do(dto);
        //这里不能直接保存userDO,直接保存userDO,
        // JPA会自动创建一个值为NULL的部门对象，然后再更新用户表
        // 应该先将外键对象设置为NULL再保存
        if(dto.getDeptid() == null){
            userDO.setDeptDO(null);
        }else {
        //级联更新部门信息
        Optional<DeptDO> deptDO = deptRepo.findById(dto.getDeptid());
        if(deptDO.isPresent()){

            userDO.setDeptDO(deptDO.get());
        }
    }

        userDAO.editUser(userDO);
    }
}
