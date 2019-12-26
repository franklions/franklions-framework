package com.franklions.example.service.impl;

import com.franklions.example.domain.RoleConverter;
import com.franklions.example.domain.RoleDO;
import com.franklions.example.domain.RoleDTO;
import com.franklions.example.domain.RoleMenuDTO;
import com.franklions.example.repository.RoleRepository;
import com.franklions.example.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-12-12
 * @since Jdk 1.8
 */
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    RoleRepository roleRepo;

    @Autowired
    RoleConverter roleConverter;

    @Override
    public Optional<RoleDTO> getRoleInfoById(Integer id) {
        Optional<RoleDO> roleOpt = roleRepo.findById(id);
        if(!roleOpt.isPresent()){
            return Optional.empty();
        }
        RoleDTO roleDto =roleConverter.entity2dto(roleOpt.get());
        return Optional.ofNullable(roleDto);
    }

    @Override
    public Optional<RoleMenuDTO> getRoleMenusById(Integer id) {

        Optional<RoleDO> roleOpt = roleRepo.findById(id);
        if(!roleOpt.isPresent()){
            return Optional.empty();
        }

        RoleMenuDTO roleMenuDTO = roleConverter.entity2RoleMenuDTO(roleOpt.get());
        return Optional.ofNullable(roleMenuDTO);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public RoleDTO addRole(RoleDTO dto) {
        RoleDO roleDO = roleConverter.dto2entity(dto);
        RoleDO saveDO = roleRepo.save(roleDO);

        return roleConverter.entity2dto(saveDO);
    }
}
