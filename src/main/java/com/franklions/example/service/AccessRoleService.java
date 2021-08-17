package com.franklions.example.service;

import com.franklions.example.domain.dto.AccessRoleDTO;
import com.franklions.example.domain.dto.PageParamRequest;
import com.franklions.example.domain.dto.PageReturnValue;
import com.franklions.example.domain.entity.AccessRoleEntity;
import com.franklions.example.domain.request.AccessRoleRequest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @Author: haoxubo
 * @Date: 2020/10/21 14:28
 */
public interface AccessRoleService {
    void createRole(String appId, AccessRoleRequest request);

    Optional<AccessRoleDTO> roleOne(String appId, String roleId);

    List<AccessRoleDTO> RoleList(String appId);

    PageReturnValue<AccessRoleDTO> listAccessRolePage(String appId, PageParamRequest request) throws IOException;

    void editRole(AccessRoleEntity entity, AccessRoleRequest request);

    Optional<AccessRoleEntity> getRoleByAppIdAndRoleId(String appId, String roleId);

    void removeRole(AccessRoleEntity entity);
}
