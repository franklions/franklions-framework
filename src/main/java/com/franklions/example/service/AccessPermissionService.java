package com.franklions.example.service;

import com.franklions.example.domain.dto.AccessPermissionDTO;
import com.franklions.example.domain.PageParamRequest;
import com.franklions.example.domain.PageReturnValue;
import com.franklions.example.domain.entity.AccessPermissionEntity;
import com.franklions.example.domain.request.AccessPermissionRequest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @Author: haoxubo
 * @Date: 2020/10/22 10:30
 */
public interface AccessPermissionService {
    void createPermission(String appId, AccessPermissionRequest request);

    Optional<AccessPermissionEntity> getPerByAppIdAndPerId(String appId, String permissionId);

    void editPermission(AccessPermissionEntity accessPermissionEntity, AccessPermissionRequest request);

    void removePermission(AccessPermissionEntity accessPermissionEntity);

    Optional<AccessPermissionDTO> permissionOne(String appId, String permissionId);

    List<AccessPermissionDTO> permissionList(String appId);

    PageReturnValue<AccessPermissionDTO> listPermissionPage(String appId, PageParamRequest request) throws IOException;
}
