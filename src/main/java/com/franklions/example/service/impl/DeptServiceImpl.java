package com.franklions.example.service.impl;

import com.franklions.example.domain.DeptConverter;
import com.franklions.example.domain.DeptDO;
import com.franklions.example.domain.DeptDTO;
import com.franklions.example.domain.UserDO;
import com.franklions.example.repository.DeptRepository;
import com.franklions.example.repository.UserRepository;
import com.franklions.example.service.IDeptService;
import org.apache.commons.lang3.StringUtils;
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
}
