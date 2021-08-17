package com.franklions.example.service.impl;

import com.franklions.example.constant.Constants;
import com.franklions.example.domain.dto.RolePermissionDTO;
import com.franklions.example.domain.entity.AccessPermissionEntity;
import com.franklions.example.domain.entity.RolePermissionEntity;
import com.franklions.example.domain.mapping.AccessPermissionConvert;
import com.franklions.example.mapper.AccessPermissionMapper;
import com.franklions.example.mapper.RolePermissionMapper;
import com.franklions.example.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: haoxubo
 * @Date: 2020/10/23 16:33
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    private RolePermissionMapper mapper;
    private AccessPermissionConvert permissionConvert;
    private AccessPermissionMapper permissionMapper;

    @Autowired
    public RolePermissionServiceImpl(RolePermissionMapper mapper,
                                     AccessPermissionConvert permissionConvert,
                                     AccessPermissionMapper permissionMapper) {
        this.mapper = mapper;
        this.permissionConvert = permissionConvert;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public List<RolePermissionDTO> userPermissionList(String appId, String userId) {
        List<AccessPermissionEntity> entities=mapper.userPermissionList(appId,userId);
        List<RolePermissionDTO> retList = entities.stream().map(a ->permissionConvert.user2dto(a))
                .collect(Collectors.toList());
        return retList;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void createRolePermissions(String appId, String roleId,List<AccessPermissionEntity> permissions) {
        List<RolePermissionEntity> entities = new ArrayList<>();
        for (AccessPermissionEntity permissionEntity : permissions) {
            RolePermissionEntity entity = new RolePermissionEntity();
            entity.setAppId(appId);
            entity.setRoleId(roleId);
            entity.setPermissionId(permissionEntity.getPermissionId());
            entity.setGmtCreated(System.currentTimeMillis());
            entity.setTs(System.currentTimeMillis());
            entities.add(entity);
        }
        mapper.createRolePermissions(entities);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void removeRolePermissions(String appId, String roleId, List<String> ids) {
        Example example = new Example(RolePermissionEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(Constants.APPID,appId)
                .andEqualTo(Constants.ROLEID,roleId)
                .andIn(Constants.PERMISSIONID,ids);
        mapper.deleteByExample(example);
    }

    @Override
    public List<RolePermissionDTO> rolePermissionList(String appId, String roleId) {
        List<AccessPermissionEntity> entities=mapper.rolePermissionList(appId,roleId);
        List<RolePermissionDTO> retList = entities.stream().map(a ->permissionConvert.user2dto(a))
                .collect(Collectors.toList());
        return retList;
    }

    @Override
    public List<AccessPermissionEntity> permissionsListById(String appId, List<String> permissions) {
        Example example = new Example(AccessPermissionEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(Constants.APPID,appId)
                .andIn(Constants.PERMISSIONID,permissions)
                .andEqualTo(Constants.DELETED,false);
        return permissionMapper.selectByExample(example);
    }
}
