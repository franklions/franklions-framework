package com.franklions.example.service;

import com.franklions.example.domain.dto.RolePermissionDTO;
import com.franklions.example.domain.entity.AccessPermissionEntity;

import java.util.List;

/**
 * @Author: haoxubo
 * @Date: 2020/10/23 16:33
 */
public interface RolePermissionService {
    List<RolePermissionDTO> userPermissionList(String appId, String userId);

    void createRolePermissions(String appId, String roleId, List<AccessPermissionEntity> entities);

    void removeRolePermissions(String appId, String roleId, List<String> ids);

    List<RolePermissionDTO> rolePermissionList(String appId, String roleId);

    List<AccessPermissionEntity> permissionsListById(String appId, List<String> permissions);

}
