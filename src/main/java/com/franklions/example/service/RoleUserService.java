package com.franklions.example.service;

import com.franklions.example.domain.dto.RoleUserDTO;
import com.franklions.example.domain.entity.AccessRoleEntity;

import java.util.List;

/**
 * @Author: haoxubo
 * @Date: 2020/10/22 15:25
 */
public interface RoleUserService {
    void createRoleUser(String appId, String userId, List<String> request);

    List<RoleUserDTO> roleUserList(String appId, String userId);

    void removeRoleUser(String appId, String userId, List<String> ids);

    List<AccessRoleEntity> roleListById(String appId, List<String> request);

    void removeRoleUserAll(String appId, String userId);

    List<String> permissionCodeUsers(String appId, String code);
}
