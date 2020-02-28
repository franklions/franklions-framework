package com.franklions.example.service.impl;

import com.franklions.example.domain.mapper.DeptConverter;
import com.franklions.example.domain.DeptDO;
import com.franklions.example.domain.DeptDTO;
import com.franklions.example.domain.UserDO;
import com.franklions.example.repository.DeptRepository;
import com.franklions.example.repository.UserRepository;
import com.franklions.example.service.IDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-12-11
 * @since Jdk 1.8
 */
@Service
public class DeptServiceImpl implements IDeptService {

    @Autowired
    DeptRepository deptRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    DeptConverter deptConverter;

    @Override
    public Optional<DeptDO> getDeptInfoById(Integer id) {
        return deptRepo.findById(id);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public DeptDO addDept(DeptDTO dto) {
        DeptDO deptDO = deptConverter.dto2entity(dto);
        return deptRepo.save(deptDO);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void removeDept(Integer id) {
        //删除部门的同时也会删除与部门相关联的用户，
        Optional<DeptDO> deptOpt = deptRepo.findById(id);
        if(deptOpt.isPresent()) {

            if(deptOpt.map(d->d.getUsers()).isPresent()){
                //1解除用户与部门的外键
                Set<UserDO> users = new HashSet();
                for (UserDO user : deptOpt.get().getUsers()) {
                    user.setDeptDO(null);
                    users.add(user);
                }
                if(users.size() >0) {
                    userRepo.saveAll(users);
                    userRepo.flush();
                }
            }
            //2解除session中与user的关系
            DeptDO deptDO = deptOpt.get();
            deptDO.setUsers(null);
            deptRepo.save(deptDO);
            //3再删除部门
            deptRepo.deleteById(id);
        }
    }
}
