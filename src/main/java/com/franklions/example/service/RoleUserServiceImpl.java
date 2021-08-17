package com.franklions.example.service;

import com.franklions.example.constant.Constants;
import com.franklions.example.domain.dto.RoleUserDTO;
import com.franklions.example.domain.entity.AccessRoleEntity;
import com.franklions.example.domain.entity.RoleUserEntity;
import com.franklions.example.domain.mapping.AccessRoleConvert;
import com.franklions.example.mapper.AccessRoleMapper;
import com.franklions.example.mapper.RoleUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: haoxubo
 * @Date: 2020/10/22 15:25
 */
@Service
public class RoleUserServiceImpl implements RoleUserService {

    private RoleUserMapper mapper;
    private AccessRoleConvert roleConvert;
    private AccessRoleMapper roleMapper;

    @Autowired
    public RoleUserServiceImpl(RoleUserMapper mapper,
                               AccessRoleConvert roleConvert,
                               AccessRoleMapper roleMapper) {
        this.mapper = mapper;
        this.roleConvert = roleConvert;
        this.roleMapper = roleMapper;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void createRoleUser(String appId, String userId, List<String> request) {
        //根据roleId验证角色表信息是否存在
        List<RoleUserEntity> roleUserEntities = new ArrayList<>();
        for (String roleId : request) {
            RoleUserEntity entity = new RoleUserEntity();
            entity.setAppId(appId);
            entity.setRoleId(roleId);
            entity.setUserId(userId);
            entity.setGmtCreated(System.currentTimeMillis());
            entity.setTs(System.currentTimeMillis());
            roleUserEntities.add(entity);
        }
        mapper.insertRoleUser(roleUserEntities);
    }

    @Override
    public List<RoleUserDTO> roleUserList(String appId, String userId) {
       List<AccessRoleEntity> entities=mapper.roleUserList(appId,userId);
       List<RoleUserDTO> retList = entities.stream().map(a ->roleConvert.roleUser2dto(a))
               .collect(Collectors.toList());
        return retList;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void removeRoleUser(String appId, String userId, List<String> ids) {
        Example example = new Example(RoleUserEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(Constants.APPID,appId)
                .andEqualTo(Constants.USERID,userId)
                .andIn(Constants.ROLEID,ids);
        mapper.deleteByExample(example);
    }

    @Override
    public List<AccessRoleEntity> roleListById(String appId,List<String> request) {
        Example example = new Example(AccessRoleEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(Constants.APPID,appId)
                .andIn(Constants.ROLEID,request)
                .andEqualTo(Constants.DELETED,false);
        return roleMapper.selectByExample(example);
    }

    @Override
    public void removeRoleUserAll(String appId, String userId) {
        Example example = new Example(RoleUserEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(Constants.APPID,appId)
                .andEqualTo(Constants.USERID,userId);
        mapper.deleteByExample(example);
    }

    @Override
    public List<String> permissionCodeUsers(String appId, String code) {
        List<String> userList=mapper.mealUserList(appId,code);
        return userList;
    }
}
