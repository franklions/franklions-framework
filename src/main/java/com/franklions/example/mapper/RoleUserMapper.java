package com.franklions.example.mapper;

import com.franklions.example.domain.entity.AccessRoleEntity;
import com.franklions.example.domain.entity.RoleUserEntity;
import com.franklions.example.mapper.provider.RoleUserMapperProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface RoleUserMapper extends Mapper<RoleUserEntity>{

    @InsertProvider(type = RoleUserMapperProvider.class,method = "insertRoleUser")
    void insertRoleUser(@Param("list") List<RoleUserEntity> roleUserEntities);

    @SelectProvider(type = RoleUserMapperProvider.class,method = "roleUserList")
    List<AccessRoleEntity> roleUserList(@Param("appId") String appId, @Param("userId") String userId);

    @SelectProvider(type = RoleUserMapperProvider.class,method = "mealUserList")
    List<String> mealUserList(@Param("appId") String appId, @Param("code") String code);
}