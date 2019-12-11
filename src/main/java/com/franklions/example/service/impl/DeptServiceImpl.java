package com.franklions.example.service.impl;

import com.franklions.example.domain.DeptDO;
import com.franklions.example.repository.DeptRepository;
import com.franklions.example.service.IDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public Optional<DeptDO> getDeptInfoById(Integer id) {
        return deptRepo.findById(id);
    }
}
