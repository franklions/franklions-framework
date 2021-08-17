package com.franklions.example.mapper;

import com.franklions.example.domain.entity.AccessPermissionEntity;
import com.franklions.example.domain.entity.RolePermissionEntity;
import com.franklions.example.mapper.provider.RolePermissionProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface RolePermissionMapper extends Mapper<RolePermissionEntity> {
    @SelectProvider(type = RolePermissionProvider.class,method = "userPermissionList")
    List<AccessPermissionEntity> userPermissionList(@Param("appId") String appId, @Param("userId") String userId);

    @InsertProvider(type = RolePermissionProvider.class,method = "createRolePermissions")
    void createRolePermissions(@Param("list") List<RolePermissionEntity> entities);

    @SelectProvider(type = RolePermissionProvider.class,method = "rolePermissionList")
    List<AccessPermissionEntity> rolePermissionList(@Param("appId") String appId, @Param("roleId") String roleId);
}